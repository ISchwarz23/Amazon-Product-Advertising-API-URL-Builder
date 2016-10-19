# Amazon-Product-Advertising-API-URL-Builder
Amazon challenges all developers that want to use their Advertising API. Especially the creation of the request URL is 
pretty complex. Therefor I've created this URL Builder, which allows you to create the request URL without having to read 
the Product Advertisement API documentation or study the used hashing algorithms.

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
// Germanc
AmazonWebServiceLocation.DE
```

## Item Lookup
### Basic Request
If you want to lookup specific item information from Amazon you can do so by creating an Request using the 
`AmazonProductAdvertisingApiRequestBuilder`. You create a request builder for an item lookup by giving the `ItemId`
of the item to search for. To create the request url you have to give the `AmazonWebServiceLocation` that shall
be requested and the `AmazonWebServiceAuthentication` that shall be used.
```java
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);
```
The result is a request URL as String, that can be used to do the request with your favourite http request library. The
result of the `createRequestUrlFor()` method will create a http link. If you prefer the https protocol in your url, simply
call the `createSecureRequestUrlFor()`.
```java
final String requestUrl = AmazonProductAdvertisingApiRequestBuilder.forItemLookup(ITEM_ID)
                .createSecureRequestUrlFor(AmazonWebServiceLocation.DE, authentication);
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
                .createRequestUrlFor(AmazonWebServiceLocation.DE, authentication);
```

### Filter the Result

