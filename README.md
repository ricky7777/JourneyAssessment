# Journey Assessment
# Overview
● This project is using Jetpack Compose to make ui, Retrofit2 is also used to call the API, and the entire project uses Hilt to control the app life cycle. In addition to adding a search function, it also adds processing when there is no network state, and adds the use of local DB in the comment screen.

● The Repository Design Pattern is employed to separate the Model layer, allowing for future data compatibility and scalability. Create RemoteDataSource and LocalDataSource demo scalability.

● The MainViewModel serves as the main logic control layer, providing the core logic for the front-end UI.

● CommonDao provides local storage when no internet still can show comments from DB.

● Add loading UI improve user experience.

## Architecture
<img width="800" alt="Journey Architecture" src="https://github.com/user-attachments/assets/e946c5c2-95a4-40ae-89ab-1fb024180c9a">

## Demo
<img width="200" src="https://github.com/user-attachments/assets/ac9dfbc8-5d53-456a-a13b-bf5a403d09e4"><br/><br/>


<h1>Unit test</h1>
<img width="600" alt="test02" src="https://github.com/user-attachments/assets/938789c7-c7f3-4a2d-820b-9a0f1b32b4a3">
<img width="600" alt="test01" src="https://github.com/user-attachments/assets/4faee4ac-ca0c-4227-9be8-1aab7a7c2490">

<h1>Use Skill</h1>
1. MVVM<br/>
2. Hilt<br/>
3. Room<br/>
4. Jetpack Compose<br/>
5. Coroutine<br/>
6. Repository pattern<br/>
7. Unit test(JUnit/Mockito)
<br/><br/>

# Task

Implement the following functionalities:

- Your task is to create a native Android app.
  ```diff
  + Created a native Android app, and use new architecture.
  ```
  
- If you can, please use Kotlin, MVVM and Android Jetpack.
  ```diff
  + All project use Kotlin language and MVVM architecture, Android Jetpack Related
  ```
  
- Use the endpoints at https://jsonplaceholder.typicode.com
  ```diff
  + Use Retrofit2 call this enpoints, post and comments api
  ```
  
- Display posts in a list.
  ```diff
  + Show posts list use Jetpack Compose and adjust little UI.
  ```
  
- Open a detailed view with associated comments when user selects an item on the posts list.
  ```diff
  + Show comments when user click post item, and make no internet mechanism.
  ```
  
- Save the data into local storage.
  ```diff
  + Save the data into local storage when first time into CommentsDetailScreen.
  ```
- Search functionality on the posts list and comments list.
  ```diff
  + Use TopAppBar widget finished this function on PostScreen and CommentsDetailScreen.
  ```
# Thinking
<img width="400" alt="thinking" src="https://github.com/user-attachments/assets/586b3cd1-5c3f-41b0-8c21-ec03ad5b29c2">
