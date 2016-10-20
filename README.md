[![Build Status](https://travis-ci.org/ISchwarz23/Amazon-Product-Advertising-API-URL-Builder.svg?branch=master)](https://travis-ci.org/ISchwarz23/Amazon-Product-Advertising-API-URL-Builder)
# The Amazon-Product-Advertising-API URL Builder
Simple builder in plain Java to create requests to the Amazon Product Advertising API.  
![APA-API-Logo](https://raw.githubusercontent.com/ISchwarz23/Amazon-Product-Advertising-API-URL-Builder/master/README/apaapi_logo.png)  
Amazon challenges all developers that want to use their Advertising API. Especially the creation of the request URL is 
pretty complex. Therefor I've created this URL Builder, which allows you to create the request URL without having to read 
the Product Advertisement API documentation or study the used hashing algorithms.

#### Setup
To use this library in your Java or Android project you can download it from jCenter (GroupId: de.codecrafters.apaarb, 
ArtifactId: apaarb) in the latest version. If you use gradle add `jCenter()` to your repositories (if not done yet) and
specify the dependency.
```
dependencies {
    ...
    compile 'de.codecrafters.apaarb:apaarb:0.9.1'
    ...
}
```
This library is written in plain java and does not depend on any other library.  
  
## Item Identification
There are multiple types of ID representations handled by Amazon. Namely they are ASIN, ISBN, UPX and EAN. So especially 
if you want to look up a specific item you have to give its ID.  
Theses IDs are represented inside this library by the `ItemId` class. To create them you simply call the matching create-
method.
```java
ItemId myAsin = ItemId.createAsin("AsinValue");
ItemId myEan  = ItemId.createEan("EanValue");
ItemId myIsbn = ItemId.createIsbn("IsbnValue");
ItemId myUpx  = ItemId.createUpx("UpxValue");
```
After the creation you are able to get the ID value and type from the object.
```java
String value = myId.getValue();
Type type = myId.getType();
```
The `Type` is an enumeration inside the `ItemId` class listing all the available ID types.

## Authentication
If you do any request to the Amazon Product Advertising API you have to authenticate yourself. This authentication 
consists of an "Associate Tag", an "AWS Access Key" and an "AWS Secret Key". In this library they are grouped inside
a data class called `AmazonWebServiceAuthentication`. This class can easily created using the factory method.
```java
AmazonWebServiceAuthentication myAuthentication = AmazonWebServiceAuthentication create("myAssociateTag", "myAwsAccessKey", "myAwsSecretKey");
```
These user authentication can be used in multiple requests.

## Service Location
Amazon distinguishes its services by location (countries). So you will get different responses for different locations 
(e.g. the currency, the available products, ...). You also have to set up your account for every location.
To simplify the selection of the service this library contains an enumeration called `AmazonWebServiceLocation`, which
contains all available service locations.
```java
// America
AmazonWebServiceLocation.COM
// Germany
AmazonWebServiceLocation.DE
// ...
```  
  
## Item Lookup
### Basic Request
If you want to lookup specific item information from Amazon you can do so by creating an Request using the 
`AmazonProductAdvertisingApiRequestBuilder`. You create a request builder for an item lookup by giving the `ItemId`
of the item to search for. To create the request url you have to give the `AmazonWebServiceLocation` that shall
be requested and the `AmazonWebServiceAuthentication` that shall be used.
```java
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```
The result is a request URL as String, that can be used to do the request with your favourite http request library. The
result of the `createRequestUrlFor()` method will create a http link. If you prefer the https protocol in your url, simply
call the `createSecureRequestUrlFor()`.
```java
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```

### Specify the included Item Information
You also have the possibility to specify which item information will be returned by the amazon service when calling
the request URL. This can be done using the `ItemInformation` enumeration, which lists all available item information
categories. If you don't pass anything the request builder will add information about the item attributes to the request.
```java
// static import was used for ItemInformation
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .includeInformationAbout(ATTRIBUTES)
                .includeInformationAbout(OFFERS)
                .includeInformationAbout(IMAGES)
                .createRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```

### Filter the Result
If you want to specify the result set, there is the possibility to do so by filtering the results by the condition of
the products. All available conditions are listed inside the `ItemCondition` enumeration. An example usage is shown below.
```java
// static import was used for ItemInformation and ItemCondition
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .includeInformationAbout(ATTRIBUTES)
                .includeInformationAbout(OFFERS)
                .includeInformationAbout(IMAGES)
                .filterByCondition(NEW)
                .createRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```  
  
## Item Search
### Basic Request
To do a basic item search request by keywords to Amazon you can do so by creating an Request using the 
`AmazonProductAdvertisingApiRequestBuilder`. You create a request builder for an item lookup by giving the keywords that
shall be searched for as string (spaces are allowed). To create the request url you have to give the again the
`AmazonWebServiceLocation` that shall be requested and the `AmazonWebServiceAuthentication` that shall be used.
```java
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch("Deadpool Movie")
                .createRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```
The result is a request URL as String, that can be used to do the request with your favourite http request library. The
result of the `createRequestUrlFor()` method will create a http link. If you prefer the https protocol in your url, simply
call the `createSecureRequestUrlFor()`.
```java
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```

### Specify the included Item Information
You also have the possibility to specify which item information will be returned by the amazon service when calling
the request URL. This can be done using the `ItemInformation` enumeration, which lists all available item information
categories. If you don't pass anything the request builder will add information about the item attributes to the request.
```java
// static import was used for ItemInformation
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemSearch("Deadpool Movie")
                .includeInformationAbout(ATTRIBUTES)
                .includeInformationAbout(OFFERS)
                .includeInformationAbout(IMAGES)
                .createRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```

### Filter the Result
If you want to specify the result set, there is the possibility to do so by filtering the results by the condition, the 
category, the minimum, as well as the maximum price of the items/products. All available conditions are listed inside 
the `ItemCondition` enumeration. The available categories are inside the enumeration called `ItemCategory`. To filter by
minimum and maximum price you have to give an integer representing the price in the smallest currency unit of the given
location. An example usage is shown below. This filters can be used separately as well as in combination.
```java
// static import was used for ItemInformation and ItemCondition
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .includeInformationAbout(ATTRIBUTES)
                .includeInformationAbout(OFFERS)
                .includeInformationAbout(IMAGES)
                .filterByCondition(NEW)
                .filterByCategroy(DVD)
                .filterByMinimumPrice(10000)
                .filterByMaximumPrice(30000)
                .createRequestUrlFor(AmazonWebServiceLocation.COM, authentication);
```