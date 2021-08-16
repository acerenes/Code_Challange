# Code_Challenge is an application that uses PixaBay API service to look for images and show their details in another activity.  

## About Application

- In this project, the desired word and words are searched using the PixaBay API service. 
- The first 24 results are displayed in MainActivity with user and tag information. 
- If the user scrolls the page, the other 24 results are loaded. 
- This shortens the time it would take to get thousands of results at once. User experience is considered. 
- If the user wants to reach the details of the desired content, he clicks on the relevant content. 
- In this section, a dialog box is displayed to the user. He is asked if he wants to continue. 
- If the user does not want to continue, he gives a negative answer. It stays in MainActivity. 
- If he wants to continue, he responds positively. The detail page opens for the relevant content. 
- On this screen, a higher quality picture of the content is displayed. 
- Under the picture, the owner of this picture, the picture's tag, likes, comments, and favorite numbers are displayed. 
- Once the images are uploaded, even if there is no internet connection, 
  the images are stored with the cache mechanism as long as the application is open. 
- The application continues to work with the same performance in both portrait and landscape mode.

## Dependencies
  List of third party libraries used in the project.

- Retrofit-2          --> A type-safe REST client for Android and Java
- Lombok              --> Used to reduce boilerplate code for model/data objects
- Glide and PhotoView --> A fast and efficient open source media management and image loading framework for Android
                      --> Zooming Android ImageView
- Custom Progress Bar --> Loading icon with animation library
- Custom Dialog Box   --> SweetAlert for Android, a beautiful and clever alert dialog
- Dagger2             --> A fast dependency injector for Java and Android.
