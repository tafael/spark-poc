package br.com.zup

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

@ConfigurationProperties(prefix = "mysql.dataset")
open class MysqlDataSetProperties {
    lateinit var connectionUrl: String
    lateinit var username: String
    lateinit var password: String
}

@Configuration
open class MySqlDataSetConfig(
    var mysqlDataSetProperties: MysqlDataSetProperties
) {

    @Bean("mysqlDataSet")
    open fun mysqlDataSet(sparkSession: SparkSession): Dataset<Row> {
        return sparkSession.read()
            .jdbc(
                mysqlDataSetProperties.connectionUrl,
                "sales",
                Properties().apply {
                    put("user", mysqlDataSetProperties.username)
                    put("password", mysqlDataSetProperties.password)
                    put("driver", "com.mysql.cj.jdbc.Driver")
                })
    }

}
