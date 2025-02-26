package com.estudos.reservas;

import org.springframework.boot.SpringApplication;

public class TestReservationSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ReservationSystemsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
