package br.com.zup.controller

import br.com.zup.SparkPocConfig
import br.com.zup.model.Result
import br.com.zup.model.Sale
import br.com.zup.model.User
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [SparkPocConfig::class])
class ControllerTest {

    @Autowired
    lateinit var controller: Controller

    @Test
    fun `should join data sets`() {
        val joinedDatasets = controller.joinDataSets()

        assertEquals(
            expected = listOf(
                Result(idUser = "user-1", name = "user-1", idSale = "sale-1"),
                Result(idUser = "user-5", name = "user-5", idSale = "sale-10"),
                Result(idUser = "user-1", name = "user-1", idSale = "sale-2"),
                Result(idUser = "user-1", name = "user-1", idSale = "sale-3"),
                Result(idUser = "user-1", name = "user-1", idSale = "sale-4"),
                Result(idUser = "user-2", name = "user-2", idSale = "sale-5"),
                Result(idUser = "user-2", name = "user-2", idSale = "sale-6"),
                Result(idUser = "user-3", name = "user-3", idSale = "sale-7"),
                Result(idUser = "user-4", name = "user-4", idSale = "sale-8"),
                Result(idUser = "user-4", name = "user-4", idSale = "sale-9")
            ),
            actual = joinedDatasets
        )
    }

    @Test
    fun `should count sales by user`() {
        val salesByUser = controller.countSalesByUser()

        assertEquals(
            expected = listOf(
                User(id = "user-1", name = "user-1") to 4L,
                User(id = "user-2", name = "user-2") to 2L,
                User(id = "user-3", name = "user-3") to 1L,
                User(id = "user-4", name = "user-4") to 2L,
                User(id = "user-5", name = "user-5") to 1L
            ),
            actual = salesByUser
        )

    }

    @Test
    fun `should find sales paged and filtered`() {
        val filteredAndPagedSales = controller.findFilteredAndPaginatedSales(2, 2, "user-1")
        assertEquals(
            expected = listOf(
                Sale(id = "sale-3", idUser = "user-1"),
                Sale(id = "sale-4", idUser = "user-1")
            ),
            actual = filteredAndPagedSales
        )
    }

}
