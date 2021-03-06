package ua.sernikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ua.sernikov.domain.UpdateUserRequest;
import ua.sernikov.domain.User;
import ua.sernikov.service.OperatorService;

import java.util.List;

@RestController
@RequestMapping(value = "/operators",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OperatorController {

    private OperatorService operatorService;

    @Autowired
    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllOperators() {
        return operatorService.getAllOperators();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{key}")
    public User getOperator(@PathVariable("key") String operatorKey) {
        Assert.hasText(operatorKey);
        return operatorService.getOperatorByKey(operatorKey);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createOperator(@RequestBody User operator) {
        return operatorService.createOperator(operator.getName(), operator.getEmail());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{key}")
    public User updateOperator(@PathVariable("key") String operatorKey, @RequestBody UpdateUserRequest updateUserRequest) {
        Assert.hasText(operatorKey);
        Assert.notNull(updateUserRequest);

        updateUserRequest.setKey(operatorKey);
        return operatorService.updateOperator(updateUserRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{key}")
    public void deleteOperator(@PathVariable("key") String operatorKey) {
        Assert.hasText(operatorKey);
        operatorService.deleteOperatorByKey(operatorKey);
    }
}
