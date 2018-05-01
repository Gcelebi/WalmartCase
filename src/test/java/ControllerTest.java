/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.walmartcase.ModelViewController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author User
 */
public class ControllerTest {
 
    @InjectMocks
    private ModelViewController mockcontroller;
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
         InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
                 viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
 
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockcontroller).setViewResolvers(viewResolver).build();
 
    }
     @Test
    public void testhome() throws Exception {

        this.mockMvc.perform(get("/home"))
                .andExpect(status().isOk());

    }
    
    @Test
    public void testhome2() throws Exception{
    this.mockMvc.perform(get("/home")
            .param("p", "-1"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("page_error"));
    }
    
    public ControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    
    
    @After
    public void tearDown() {
    }

}
