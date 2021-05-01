### Dependencies

* Latest Android Studio

### Configuration

To get basic idea about configuration approach read [12factor](https://12factor.net/).

Different build types are expected to have different configuration and it is stored in `.properties` files. Inside app module, by default, `debug.properties` and `release.properties` are expected.

## Getting started

### Kotlin styleguides

This project is following **official Kotlin codestyle**. It follows both codestyle from [kotlinlang.org](https://kotlinlang.org/docs/reference/coding-conventions.html) and [Android Kotlin styleguide](https://developer.android.com/kotlin/style-guide).

### Android file naming conventions

This project is following this [Android naming conventions](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md) 

### New feature

Use [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow) development workflow with tests included.

## Tools

### Networking with OkHttp + Retrofit

Networking is implemented via [Retrofit](https://square.github.io/retrofit/), with Http client provided by [OkHttp](http://square.github.io/okhttp/) that provides easy API communication and response parsing using [Gson](https://github.com/google/gson).

### Databinding + Android Architecture Components ViewModel

MVVM is implemented with help of [databinding](https://developer.android.com/topic/libraries/data-binding/) and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) of [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

### Dependency injection with Dagger2

All dependencies in application are injected using [JSR-330](https://www.jcp.org/en/jsr/detail?id=330)(`@Inject`, `@Singleton`) annotations. 
[Dagger2](https://google.github.io/dagger/) is used to make all of it easier. 

### Kotlin Coroutines

Communication between `ViewModel` and `Repository` relies on Kotlin [coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
 
### Testing

// Mockk

### Features
-Browsing trough the comics
-Favorite specific comic
-List of favourite comics (add, delete)
-Offline access to favorite comics
-Share comic
-Subscribe to new comics updates
-Comic details

## Maintainers

jstovrag@gmail.com
