package br.com.zup.controller

import br.com.zup.model.Result
import br.com.zup.model.Sale
import br.com.zup.model.User
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
open class Controller(
    @Qualifier("mysqlDataSet")
    private val salesDataSet: Dataset<Row>,
    @Qualifier("postgresDataSet")
    private val usersDataSet: Dataset<Row>
) {

    @PostMapping("join-data-sets")
    @ResponseBody
    fun joinDataSets(): List<Result> {
        return usersDataSet
            .join(
                salesDataSet,
                salesDataSet.col("id_user").equalTo(usersDataSet.col("id"))
            )
            .orderBy(salesDataSet.col("id"))
            .collectAsList().map {
                Result(
                    it.getString(0),
                    it.getString(1),
                    it.getString(2)
                )
            }
    }

    fun countSalesByUser(): List<Pair<User, Long>> {
        val groupedSalesDataSet = salesDataSet.groupBy(salesDataSet.col("id_user")).count()
        return groupedSalesDataSet
            .join(
                usersDataSet,
                usersDataSet.col("id").equalTo(groupedSalesDataSet.col("id_user"))
            )
            .orderBy(usersDataSet.col("name"))
            .collectAsList().map {
                User(
                    id = it.getString(2),
                    name = it.getString(3)
                ) to it.getLong(1)
            }
    }

    fun findFilteredAndPagedSales(page: Int, pageSize: Int, idUser: String): List<Sale> {
        val filteredSalesDataSet = salesDataSet
            .filter(
                salesDataSet.col("id_user").equalTo(idUser)
            )

        val windowSpec = Window.partitionBy().orderBy(filteredSalesDataSet.col("id").asc())

        return filteredSalesDataSet
            .select(
                filteredSalesDataSet.col("*"),
                functions.row_number().over(windowSpec).alias("rowNumber")
            )
            .filter(
                functions.col("rowNumber").gt((page - 1) * pageSize)
            )
            .orderBy(filteredSalesDataSet.col("id"))
            .limit(pageSize)
            .collectAsList().map {
                Sale(
                    id = it.getString(0),
                    idUser = it.getString(1)
                )
            }
    }

}
