package com.example.truckweightbridge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.truckweightbridge.repository.local.Ticket
import com.example.truckweightbridge.usecase.TicketUseCase
import com.example.truckweightbridge.util.Response
import com.example.truckweightbridge.viewModel.TicketFormViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TicketFormViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var firebaseRepository: TicketUseCase

    private lateinit var viewModel: TicketFormViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxed = true)
        viewModel = TicketFormViewModel(firebaseRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `validate with valid data`() {
        var ticket = Ticket(dateTime = "2023-06-25",
            driverName = "John Doe", licenseNumber = "1223434",
            inboundWeight = "100", outboundWeight = "90", netWeight = "10")
        viewModel.validate(
           ticket
        )

        val validationResult = viewModel.validationResult.value
        assert(validationResult.isDateTimeValid)
        assert(validationResult.isDriverNameValid)
        assert(validationResult.isLicenceNumberValid)
        assert(validationResult.isInboundWeightValid)
        assert(validationResult.isOutboundWeightValid)
        assert(viewModel.isValid.value?.first?:false)
    }

    @Test
    fun `validate with invalid data`() {
        var ticket = Ticket(dateTime = "",
            driverName = "", licenseNumber = "abc123",
            inboundWeight = "abc", outboundWeight = "", netWeight = "")
        viewModel.validate(
           ticket
        )

        val validationResult = viewModel.validationResult.value
        assert(!validationResult.isDateTimeValid)
        assert(!validationResult.isDriverNameValid)
        assert(!validationResult.isLicenceNumberValid)
        assert(!validationResult.isInboundWeightValid)
        assert(!validationResult.isOutboundWeightValid)
    }

    @Test
    fun `addTicket calls firebaseRepository addTicket`() = runTest {
        var ticket = Ticket(dateTime = "2023-06-25",
            driverName = "John Doe", licenseNumber = "1223434",
            inboundWeight = "100", outboundWeight = "90", netWeight = "10")

        coEvery { firebaseRepository.addTicket(ticket) } returns Response.Success(true)

        viewModel.addTicket(ticket)
        coVerify { firebaseRepository.addTicket(ticket) }
        assert(viewModel.addTicketResponse.value is Response.Success)
    }

}
