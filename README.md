# Imgur-Image-Search

Simple image searching app that allows you to add comments to images (JPEG & PNG).
#### Things implemented :
* Architecture - MVVM
* MainActivity is implemented in Java.
* DetailImageActivity is implemented in Kotlin.
* ProgressDialogs shown while loading data.
* Add a comment to the opened image.
* Use of Constraint layouts, RecyclerView, GridLayout.
* Debounce of 250ms for search operation.
* Generic error toast shown in case for API failures.



#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **ui**: View classes along with their corresponding ViewModel.
4. **utils**: Utility classes.

#### Components Used :
* LiveData -Architecture Component
* ViewModel - Architecture Component
* RxJava & RxAndroid - For asynchronous tasks
* ButterKnife - Used for Data Binding
* Picasso - To Load images from URL
* Room - For database operations
* Retrofit & OKHTTP - Handles network calls


