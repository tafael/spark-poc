package br.com.zup.controller

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
open class Controller(
    @Qualifier("mysqlDataSet")
    private val mysqlDataSet: Dataset<Row>,
    @Qualifier("postgresDataSet")
    private val postgresDataSet: Dataset<Row>
) {

    @PostMapping("join-data-sets")
    @ResponseBody
    fun joinDataSets(): List<String> {
        return postgresDataSet.join(
            mysqlDataSet, mysqlDataSet.col("id_user").equalTo(postgresDataSet.col("id"))
        ).collectAsList().map { it.mkString() }
    }

}