### What is this repository for? ###

* Rxjava2 and RxAndroid implementation in android application.
* Observable Example
* Flowable Example
* How to convert Observable to Flowable and vice versa
* fromCallable operator Example
* Creating an Observable from a single object
* Creating Observable from list of objects
* Dispose the observer if it's no longer needed.

### Dependencies

* Rxjava2
* RxAndroid

### How do I get set up? ###

* Add rxjava2 dependency(s) in app/build.gradle.
```
implementation "io.reactivex.rxjava2:rxjava:2.x.y" // 2.2.7
implementation "io.reactivex.rxjava2:rxandroid:2.x.y" // 2.1.1
```
* Create User model.
* Create DataSource class that has static function to create users list.
* Create Observable of User using fromIterable operator.
* Subscribe Observable from an Observer and Disposable to CompositeDisposable
* Clear Observer using CompositeDisposable.
