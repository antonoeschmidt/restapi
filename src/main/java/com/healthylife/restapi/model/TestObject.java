package com.healthylife.restapi.model;

public class TestObject {
    private String testName;
    private int testAge;

    public TestObject(String testName, int testAge) {
        this.testName = testName;
        this.testAge = testAge;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestAge() {
        return testAge;
    }

    public void setTestAge(int testAge) {
        this.testAge = testAge;
    }
}
