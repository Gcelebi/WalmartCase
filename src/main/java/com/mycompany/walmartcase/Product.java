/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.walmartcase;

import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class Product {    
private String itemId;
private String name;
private String salePrice;
private String shortDescription;
private String longDescription;
private String mediumImage;
private String largeImage;
private String customerRating;
private String customerRatingImage;
}
