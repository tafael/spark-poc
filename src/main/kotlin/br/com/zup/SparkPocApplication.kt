package br.com.zup

import org.apache.logging.log4j.LogManager
import org.apache.spark.sql.SparkSession
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
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
        DataSourceAutoConfiguration::class
    ]
)
open class SparkPocConfig {

    @Bean
    open fun sparkSession(): SparkSession {
        return SparkSession.builder()
            .master("local[*]")
            .appName("Spark2JdbcDs")
            .getOrCreate()
    }

}

object SparkPocApplication : Serializable {

    private val logger = LogManager.getLogger(SparkPocApplication::class)

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SparkPocConfig::class.java, *args)
        logger.info("Spark poc application started successfully !!!")
    }

}
