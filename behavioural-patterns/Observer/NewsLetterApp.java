package Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Article
record  Article(String name, String body) {}

//News
class News{
    List<Article> newsArticles = new ArrayList<>();

    public void addArticle(Article article){
        newsArticles.add(article);
    }

    public List<Article> getArticles(){
        return newsArticles;
    }

}

//Observer Interface
interface User{
    public void update(News news);
    public String getId();
}

//Concrete Observer
class NewsLetterUser implements User{
    private String name;
    private String userId;
    private News news;

    public NewsLetterUser(String name, String userId){
        this.name = name;
        this.userId = userId;
    }

    public void update(News news){
        this.news = news;
        readNews();
    }

    public String getId(){
        return userId;
    }

    public void readNews(){
        System.out.println("User: " + name);
        System.out.println("Reading the Kunsel News");
        for(Article article: news.getArticles()){
            System.out.println(article);
        }
    }

}

//Real Subject Interface
interface NewsLetter{
    public void subscribe(User user);
    public void unSubscribe(User user);
    public void notifySubscriber();
    public void publish(News news);
}

//Concrete Subject
class Kunesel implements NewsLetter{
    private HashMap<String, User> subscribers = new HashMap<>();
    private News news;
    
    public void subscribe(User user){
        subscribers.put(user.getId(), user);
    }
    public void unSubscribe(User user){
        subscribers.remove(user.getId());
    }

    public void notifySubscriber(){
        for(HashMap.Entry<String, User> user: subscribers.entrySet()){
            user.getValue().update(news);
        }
    }

    public void publish(News news){
        this.news = news;
        notifySubscriber();
    }

}

//Client
public class NewsLetterApp {
    public static void main(String[] args){
        //Lets build the Kunsel News First
        News kuneslNews = new News();
        kuneslNews.addArticle(new Article("Rising Temperature", "The global warming is causing the rise in the temperature"));
        kuneslNews.addArticle(new Article("Forest Fire", "The forest fire broke out in the capital"));

        //Real Subject
        Kunesel kunesel = new Kunesel();
        kunesel.subscribe(new NewsLetterUser("Sonam", "sonam123"));
        kunesel.subscribe(new NewsLetterUser("John", "john123"));

        //Lets publish the news
        kunesel.publish(kuneslNews); //Should see all the user in the console with the news

        kunesel.unSubscribe(new NewsLetterUser("John", "john123"));
        kunesel.publish(kuneslNews); // Publish same news, only Sonam should see the news
    }
}
