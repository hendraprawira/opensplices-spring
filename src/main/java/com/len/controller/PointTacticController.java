package com.len.controller;

import com.len.model.PointTactic;
import com.len.model.PointTacticRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class PointTacticController {

    private final PointTacticRepository repository;

    public PointTacticController(PointTacticRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/tactic-point")
    List<PointTactic> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/tactic-point")
    PointTactic newPointTactic(@RequestBody PointTactic newPointTactic) {
        System.out.println("A"+newPointTactic);
        System.out.println(Arrays.toString(newPointTactic.getCoordinates()));

        return repository.save(newPointTactic);
    }

    // Single item

    @GetMapping("/tactic-point/{id}")
    PointTactic one(@PathVariable int id) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/tactic-point/{id}")
    PointTactic replaceEmployee(@RequestBody PointTactic newPointTactic, @PathVariable int id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setOpacity(newPointTactic.getOpacity());
                    employee.setAltitude(newPointTactic.getAltitude());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newPointTactic.setId(id);
                    return repository.save(newPointTactic);
                });
    }

    @DeleteMapping("/tactic-point/{id}")
    void deleteEmployee(@PathVariable int id) {
        repository.deleteById(id);
    }
}

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(int id) {
        super("Could not find employee " + id);
    }
}

