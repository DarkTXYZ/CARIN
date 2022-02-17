package com.carin.API;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.*;

@Entity
class Employee {

    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    @ElementCollection // 1
    @CollectionTable(name = "my_list", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "nums") // 3
    private List<Integer> nums;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="example_attributes", joinColumns=@JoinColumn(name="example_id"))
    Map<String , Integer> mapped;

    Employee() {}

    Employee(String name, String role , List<Integer> nums , Map<String , Integer> mapped) {
        this.nums = nums;
        this.name = name;
        this.role = role;
        this.mapped = mapped;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public Map<String, Integer> getMapped() { return mapped; }

    public List<Integer> getNums() {
        return nums;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMapped(Map<String,Integer> mapped) {this.mapped = mapped;}

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee employee))
            return false;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}' +
                ", nums=" +'\'' + nums + '\'';
    }


}