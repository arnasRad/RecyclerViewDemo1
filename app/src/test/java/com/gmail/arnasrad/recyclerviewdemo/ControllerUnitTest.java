package com.gmail.arnasrad.recyclerviewdemo;

import android.view.View;

import com.gmail.arnasrad.recyclerviewdemo.data.DataSourceInterface;
import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;
import com.gmail.arnasrad.recyclerviewdemo.logic.Controller;
import com.gmail.arnasrad.recyclerviewdemo.view.ViewInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitTest {

    /**
     * Test Doubles:
     * Specifically a "Mock"
     */
    @Mock
    DataSourceInterface dataSource;

    @Mock
    ViewInterface view;

    @Mock
    View testViewRoot;

    Controller controller;

    private static final ListItem TEST_ITEM = new ListItem(
            "6:30AM 06/01/2017",
            "Check out content like Fragmented Podcast to expose yourself to the knowledge, ideas, " +
                    "and opinions of experts in your field",
            R.color.RED
    );

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        controller = new Controller(view, dataSource);
    }

    @Test
    public void onGetListDataSuccessful() {
        // Set up any data we need for the Test
        ArrayList<ListItem> listOfData = new ArrayList<>();
        listOfData.add(TEST_ITEM);

        // Set up our Mocks to return the Data we want
        Mockito.when(dataSource.getListOFData())
                .thenReturn(listOfData);

        // Call the method(Unit) we are testing
        controller.getListFromDataSource();

        // Check how the Tested Class responds to the data it receives
        // or test it's behaviour
        Mockito.verify(view).setUpAdapterAndView(listOfData);
    }

    @Test
    public void onListItemClicked() {
        controller.onListItemClick(TEST_ITEM, testViewRoot);

        Mockito.verify(view).startDetailActivity(
                TEST_ITEM.getDateAndTime(),
                TEST_ITEM.getMessage(),
                TEST_ITEM.getColorResource(),
                testViewRoot);
    }

    // @Test
    // public void onGetListDataUnsuccessful() {
    /**************************
     *
     * Unit Test Homework:
     *
     * Implement the "View", so that when we don't recieve a List, it shows some kind of
     * error message to the user. This is common practice that you should learn!
     *
     * I've written some hints you'll have to decipher into Java code:
     *************************/
    //1 Set up your Mock dataSource

    //2 Call the method you wish to test on the Controller

    //3 Verify that the View has been told to show a message (I'd suggest showing a Toast for now)

    //Profit???

// }
}