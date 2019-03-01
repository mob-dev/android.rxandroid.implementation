public class DataSource {
  public static List<User> createUsersList(){
        List<User> users = new ArrayList<>();
        users.add(new User("Xyz"));
        users.add(new User("abc"));
        users.add(new User("def"));
        users.add(new User("ijk"));
        users.add(new User("lmn"));
        return users;
    }
}