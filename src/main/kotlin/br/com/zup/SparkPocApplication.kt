package br.com.zup

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.Serializable

@SpringBootApplication
@EnableConfigurationProperties(
    MysqlDataSetProperties::class,
    PotgresDataSetProperties::class
)
@Configuration
@EnableAutoConfiguration(
    exclude = [
        DataSourceAutoConfiguration::class,
        GsonAutoConfiguration::class
    ]
)
open class SparkPocConfig {

    @Bean
    open fun sparkSession(
        @Value("\${spark.session.app-name}")
        appName: String
    ): SparkSession {
        return SparkSession.builder()
            .master("spark://localhost:7077")
            .appName(appName)
            .getOrCreate()
    }

}

object SparkPocApplication : Serializable {

    private val logger = Logger.getLogger(SparkPocApplication::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SparkPocConfig::class.java, *args)
        logger.info("Spark poc application started successfully !!!")
    }

}
