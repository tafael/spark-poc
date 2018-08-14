package br.com.zup

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

@ConfigurationProperties(prefix = "postgres.dataset")
open class PotgresDataSetProperties {
    lateinit var connectionUrl: String
    lateinit var username: String
    lateinit var password: String
}

@Configuration
open class PostgresDataSetConfig(
    val potgresDataSetProperties: PotgresDataSetProperties
) {

    @Bean("postgresDataSet")
    open fun mysqlDataSet(sparkSession: SparkSession): Dataset<Row> {
        return sparkSession.read()
            .jdbc(
                potgresDataSetProperties.connectionUrl,
                "public.user",
                Properties().apply {
                    put("user", potgresDataSetProperties.username)
                    put("password", potgresDataSetProperties.password)
                })
    }

}
