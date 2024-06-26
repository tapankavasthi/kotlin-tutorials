package com.baeldung.openapi.api

import com.baeldung.openapi.car.apis.CarsApi
import com.baeldung.car.models.Car
import com.baeldung.car.models.CarBody
import com.baeldung.openapi.service.CarService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import com.baeldung.openapi.service.Car as CarModel
import java.time.Year

@RestController
class CarCrudController(private val carService: CarService) : CarsApi {
    override fun createCar(carBody: CarBody): ResponseEntity<Car> {
        val (model, make, year) = carBody
        return carService.createCar(model, make, Year.of(year))
          .let { ResponseEntity.ok(it.toApiCar()) }
    }

    override fun getCar(id: Int): ResponseEntity<Car> {
        return carService.getCar(id)?.toApiCar()
          ?.let { ResponseEntity.ok(it) }
          ?: throw IllegalArgumentException("Car by id $id is not found")
    }

    override fun getCars(): ResponseEntity<List<Car>> {
        return carService.getAllCars().map { it.toApiCar() }
          .let { ResponseEntity.ok(it) }
    }

    private fun CarModel.toApiCar(): Car = Car(
      id = id,
      model = model,
      make = make,
      year = year.value,
    )
}