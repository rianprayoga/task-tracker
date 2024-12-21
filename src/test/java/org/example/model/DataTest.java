package org.example.model;


import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class DataTest {

    @Test
    public void dataEmpty_hasZeroSize(){
        Data actual = Data.empty();

        assertThat(actual).extracting(Data::findAll)
                .returns(0, List::size);
    }

    @Test
    public void addTask() {
        Data actual = Data.empty();
        actual.addTask("Do homework");
        actual.addTask("Make breakfast");

        assertThat(actual.findAll())
                .hasSize(2)
                .extracting("id")
                .containsExactly(1, 2);
//                .extracting();

    }

}