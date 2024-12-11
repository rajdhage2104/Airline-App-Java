package com.airline.controller;

import com.airline.model.Flight;
import com.airline.model.FlightWithIP;
import com.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
// @RequestMapping("/flight")
@CrossOrigin(origins = "*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/")
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    //To Add flight
    @PostMapping("/")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight){
        Flight flight1 = flightService.addFlight(flight);
        return new ResponseEntity<>(flight1, HttpStatus.CREATED);
    }

    //To Get all flights
//    @GetMapping("/")
//    public ResponseEntity<List<Flight>> getAllFlight(){
//        List<Flight> allFlight = flightService.getAllFlight();
//        return new ResponseEntity<>(allFlight,HttpStatus.OK);
//    }

    @GetMapping("/")
    public ResponseEntity<List<FlightWithIP>> getAllFlight() throws UnknownHostException {
        List<Flight> allFlights = flightService.getAllFlight();
        String serverIP = InetAddress.getLocalHost().getHostAddress();

        List<FlightWithIP> flightsWithIP = allFlights.stream()
                .map(flight -> new FlightWithIP(flight, serverIP))
                .collect(Collectors.toList());

        return new ResponseEntity<>(flightsWithIP, HttpStatus.OK);
    }


    //To Get flight
    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> getFlight(@PathVariable("flightId") int flightId) throws Exception {
        return ResponseEntity.ok(flightService.getFlight(flightId));
    }

    //To delete flight
    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable("flightId") int flightId){
        flightService.deleteFlight(flightId);
        return new ResponseEntity<String>("Flight ID: "+flightId,HttpStatus.OK);
    }

    //To update flight
    @PutMapping("/{flightId}")
    public ResponseEntity<Flight> updateFlight(@PathVariable("flightId") int flightId,@RequestBody Flight flight) throws Exception {
        flight.setFlightId(flightId);
        Flight flight1 = flightService.updateFlight(flightId,flight);
        return new ResponseEntity<>(flight1,HttpStatus.OK);
    }

}
