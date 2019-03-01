public class MainActivity extends AppCompatActivity {

  CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      observableExample();
      flowableExample();
      observableToFlowable();
      flowableToObservable();
      createObservableFromSingleObject();
      creatingObservableFromListOfObjects();
      fromCallableExample();
    }

    public void fromCallableExample() {
      // create Observable (method will not execute yet)
      Observable<User> callable = Observable
                                  .fromCallable(new Callable<User>() {
                                      @Override
                                      public User call() throws Exception {
                                          return DataSource.getUsersList();
                                      }
                                  })
                                  .subscribeOn(Schedulers.io())
                                  .observeOn(AndroidSchedulers.mainThread());

      // method will be executed since now something has subscribed
      callable.subscribe(new Observer<User>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(User user) {
              Log.d(TAG, "onNext: : " + user.getName());
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
      });
    }

    public void creatingObservableFromListOfObjects() {
      // Create the Observable
      Observable<User> userListObservable = Observable
                                            .create(new ObservableOnSubscribe<User>() {
                                                @Override
                                                public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                                                    // Inside the subscribe method iterate through the list of users and call onNext(user)
                                                    for(User user: DataSource.createUsersList()){
                                                        if(!emitter.isDisposed()){
                                                            emitter.onNext(user);
                                                        }
                                                    }
                                                    // Once the loop is complete, call the onComplete() method
                                                    if(!emitter.isDisposed()){
                                                        emitter.onComplete();
                                                    }

                                                }
                                            })
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread());

      // Subscribe to the Observable and get the emitted objects
      userListObservable.subscribe(new Observer<User>() {
      @Override
      public void onSubscribe(Disposable d) {
        compositeDisposable.add(d);

      }

      @Override
      public void onNext(User user) {
          Log.d(TAG, "onNext: user list: " + user.getName());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
      });
    }

    public void createObservableFromSingleObject() {
      // Creating an Observable from a single object
      // Instantiate the object to become an Observable
      final User user = new User("Xyz");

      // Create the Observable
      Observable<User> singleTaskObservable = Observable
              .create(new ObservableOnSubscribe<User>() {
                  @Override
                  public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                      if(!emitter.isDisposed()){
                          emitter.onNext(user);
                          emitter.onComplete();
                      }
                  }
              })
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());

      // Subscribe to the Observable and get the emitted object
      singleTaskObservable.subscribe(new Observer<User>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(User user) {
              Log.d(TAG, "onNext: single user: " + user.getName());
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
      });
    }

    public void flowableExample() {
      // with 1 million integers, this will not cause an Out of Memory Exception.
      Flowable<Integer> flowables = Flowable.range(0, 1000000)
      .onBackpressureBuffer()
      .observeOn(Schedulers.computation());
    
      flowables.subscribe(new FlowableSubscriber<Integer>() {
          @Override
          public void onSubscribe(Subscription s) {

          }
          @Override
          public void onNext(Integer integer) {
              Log.d(TAG, "onNext: " + integer);
          }
          @Override
          public void onError(Throwable t) {
              Log.e(TAG, "onError: ", t);
          }
          @Override
          public void onComplete() {

          }
      });
    }

    public void observableExample() {
      Observable<User> userObservable = Observable // create a new Observable object imported from io.reactivex lib
              .fromIterable(DataSource.createUsersList()) // apply 'fromIterable' operator
              .subscribeOn(Schedulers.io()) // designate worker thread (background)
              .observeOn(AndroidSchedulers.mainThread()); // designate observer thread (main thread)

      userObservable.subscribe(new Observer<User>() {
          @Override
          public void onSubscribe(Disposable d) {
              compositeDisposable.add(d);
              Log.e(TAG, "onSubscribe: Called.");
          }
          @Override
          public void onNext(User user) { // run on main thread
              Log.d(TAG, "onNext: " + Thread.currentThread().getName());
              Log.d(TAG, "onNext: " + user.getName());
          }
          @Override
          public void onError(Throwable e) {
              Log.e(TAG, "onError: ", e);
          }
          @Override
          public void onComplete() {
              Log.e(TAG, "onComplete: Called.");
          }
      });
    }

    public void observableToFlowable() {
      Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
      Flowable<Integer> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public void flowableToObservable() {
      Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
      Flowable<Integer> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
      Observable<Integer> backToObservable = flowable.toObservable();
    }
  
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}