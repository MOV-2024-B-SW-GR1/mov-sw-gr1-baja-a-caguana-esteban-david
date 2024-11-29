package org.example;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        //val = declarar variables inmutables
        val inmutable = "Adrian";
        // var = declare mutable variables
        var mutable = "Vicente";

        // DUCK  TYPING
        val example =  "body34";
        example.trim();
        val age: Int = 12;
        val professorName: String = "Oso body";
        val salary: Double = 1.23;
        val civilState: Character = 'C';
        varl birthDate:Date = Date();

        //WHEN == Switch Case
        when (civilState) {
            "C" -> {
                println("Married")
            }
            "S" -> {
                println("Single")
            }
        }
        //ternary operator
        val isAdult = if (age > 29) "Adult Person" else  "Children or teenager";
    }

    //Unit == void
    fun printName(name: String): Unit {
        fun anotherPrintFunctionWithoutType(type: String) {
            println("Print a function inside another function");
        }
        //Template Strings
        println("Name: ${name} ")//Form 1: variable or operations
        println("Name V2: $name")//Form 2 only if i call a variable value
    }
}