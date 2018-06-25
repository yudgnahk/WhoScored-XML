/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.crawler;

import whoscored.data.Club;
import whoscored.data.Goal;
import whoscored.data.League;
import whoscored.data.Match;
import whoscored.data.Player;
import whoscored.data.PlayerMatchStatistic;
import whoscored.data.RedCard;
import whoscored.data.TeamMatchStatistic;
import whoscored.data.YellowCard;
import whoscored.function.ClubFunction;
import whoscored.function.GoalFunction;
import whoscored.function.LeagueFunction;
import whoscored.function.MatchFunction;
import whoscored.function.PlayerFunction;
import whoscored.function.PlayerMatchStatisticFunction;
import whoscored.function.RedCardFunction;
import whoscored.function.TeamMatchStatisticFunction;
import whoscored.function.YellowCardFunction;
import static java.lang.Character.isDigit;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author haleduykhang
 */
public class WhoScoredCrawler {

    private WebDriver driver;
    private String mainURL;
    private String content;
    private static final String[] bigSixLeagues = {"Premier League", "Serie A",
        "La Liga", "Bundesliga", "Ligue 1", "UEFA Champions League"};
    private static final String[] bigSixTitles = {"England", "Italy", "Spain",
        "Germany", "France", "Europe"};

    private static final String[] bigFiveLeagues = {"Premier League", "Serie A",
        "La Liga", "Bundesliga", "Ligue 1"};
    private static final String[] bigFiveTitles = {"England", "Italy", "Spain",
        "Germany", "France"};

    public WhoScoredCrawler() {
        this.driver = new ChromeDriver();
    }

    public void setMainURL(String mainURL) {
        this.mainURL = mainURL;
        this.driver.get(mainURL);
        this.content = this.driver.getPageSource();
    }

    public void close() {
        this.driver.quit();
    }

    public void crawlBigFiveLeagues() {
        List<WebElement> anchors = driver.findElements(By.xpath("//a[@class='pt iconize iconize-icon-left']"));
        for (WebElement mver : anchors) {
            League league = new League();

            String url = mver.getAttribute("href");
            String title = mver.getAttribute("title");
            String text = mver.getText();

            for (int i = 0; i < bigFiveLeagues.length; i++) {
                if (text.equals(bigFiveLeagues[i]) && title.equals(bigFiveTitles[i])) {

                    league.setName(text);
                    league.setRegion(title);
                    league.setUrl(url);

                    LeagueFunction.addLeague(league);

                }
            }

        }
    }

    public List<String> getTeamURLs() {
        List<String> list = new ArrayList<>();
        List<WebElement> anchors = driver.findElements(By.xpath("//tbody[@class='standings']/tr/td/a[@class='team-link ']"));
        for (WebElement mver : anchors) {
            String url = mver.getAttribute("href");
            list.add(url);
        }
        return list;
    }

    public void crawlClubs(League league) {
        this.setMainURL(league.getUrl());
        List<WebElement> anchors = driver.findElements(By.xpath("//tbody[@class='standings']/tr/td/a[@class='team-link ']"));
        for (WebElement ele : anchors) {
            String url = ele.getAttribute("href");
            String name = ele.getText();
            Club club = new Club();
            club.setLeagueID(league);
            club.setName(name);
            club.setUrl(url);
            ClubFunction.addClub(club);
        }
    }

    public Map<String, String> parsePlayersList(Club club) {
        Map<String, String> result = new HashMap<>();
        this.setMainURL(club.getUrl());
        List<WebElement> divs = driver.findElements(By.xpath("//tbody[@id='player-table-statistics-body']/tr/td[@class='pn']/a[@class='player-link']"));
        for (WebElement ele : divs) {
            String url = ele.getAttribute("href");
            String name = ele.getText();
            String fixedUrl = new String(url.getBytes(Charset.forName("utf-8")));
            result.put(name, fixedUrl);
        }
        return result;
    }

    public void crawlPlayers(Club club) throws ParseException {

        Map<String, String> playersList = this.parsePlayersList(club);

        for (Map.Entry<String, String> player : playersList.entrySet()) {
            String url = player.getValue();
            this.setMainURL(url);
            List<WebElement> infos = driver.findElements(By.xpath("//dl[@class='player-info-block']"));
            Player p = new Player();
            for (WebElement info : infos) {
                WebElement dt = info.findElement(By.cssSelector("dt"));
                WebElement dd = info.findElement(By.cssSelector("dd"));

                String sDT = dt.getText();
                String sDD = dd.getText();

                String fixedDd = new String(sDD.getBytes(Charset.forName("utf-8")));
                switch (sDT) {
                    case "Name:":
                        p.setName(fixedDd);
                        break;
                    case "Current Team:":
                        p.setCurrentTeamID(club);
                        break;
                    case "Shirt Number:":
                        p.setShirtNum(Integer.parseInt(fixedDd));
                        break;
                    case "Positions:":
                        p.setPosition(fixedDd);
                        break;
                    case "Age:":
                        WebElement age = dd.findElement(By.cssSelector("i"));
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        p.setDob(sdf.parse(age.getText()));
                        break;
                    case "Height:":
                        p.setHeight(Integer.parseInt(sDD.substring(0, sDD.length() - 2)));
                        break;
                    case "Weight:":
                        p.setWeight(Integer.parseInt(sDD.substring(0, sDD.length() - 2)));
                        break;
                    case "Nationality:":
                        p.setNationality(sDD);
                        break;
                }
            }
            PlayerFunction.addPlayer(p);
        }

    }

    public List<String> crawlMatchLink(League league) throws InterruptedException {
        this.setMainURL(league.getUrl());
        WebElement fixtureLink = driver.findElement(By.xpath("//a[@id='link-fixtures']"));
        String fixtureURL = fixtureLink.getAttribute("href");
        this.setMainURL(fixtureURL);

        List<String> matchLink = new ArrayList<>();
        List<WebElement> matchesLink = driver.findElements(By.xpath("//a[@class='result-1 rc']"));

        WebElement time = driver.findElement(By.xpath("//a[@id='date-config-toggle-button']/span[@class='text']"));
        String timeText = time.getText();

        for (WebElement e : matchesLink) {
            matchLink.add(e.getAttribute("href"));
        }

        WebDriverWait wdw = new WebDriverWait(driver, 5);

        do {

            WebElement button;
            try {
                button = driver.findElement(By.xpath("//a[@class='previous button ui-state-default rc-l is-default']"));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", button);
            Thread.sleep(10000);

            wdw.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver f) {
                    return ((JavascriptExecutor) f).executeScript("return document.readyState").equals("complete");
                }

            });

            time = driver.findElement(By.xpath("//a[@id='date-config-toggle-button']/span[@class='text']"));
            timeText = time.getText();

            matchesLink = new ArrayList<>();
            matchesLink = driver.findElements(By.xpath("//a[@class='result-1 rc']"));

            for (WebElement e : matchesLink) {
                matchLink.add(e.getAttribute("href"));
            }

        } while (!timeText.contains("Aug"));

        return matchLink;
    }

    public void crawlMatches(League league) throws ParseException {

        final String currentSeason = "2017/2018";

        List<String> matchLinks = new ArrayList<>();
        try {
            matchLinks = this.crawlMatchLink(league);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 43; i < matchLinks.size(); i++) {

            this.setMainURL(matchLinks.get(i));

            // start Match Info Crawling
            WebElement result = driver.findElement(By.xpath("//td[@class='result']"));
            List<WebElement> teams = driver.findElements(By.xpath("//td[@class='team']"));

            Match match = new Match();
            String[] res = result.getText().split(" : ");
            match.setHomeScore(Integer.parseInt(res[0]));
            match.setAwayScore(Integer.parseInt(res[1]));

            String title = driver.getTitle().trim();
            int posIndex = title.lastIndexOf("-");
            title = title.substring(0, posIndex).trim();
            String[] names = title.split("\\d+-\\d+");
            String homeN = names[0];
            String awayN = names[1];

            while ((homeN.charAt(homeN.length() - 1)) == ' '
                    || (isDigit(homeN.charAt(homeN.length() - 1)))) {
                homeN = homeN.substring(0, homeN.length() - 1);
            }

            while ((awayN.charAt(0)) == ' '
                    || (isDigit(awayN.charAt(0)))) {
                awayN = awayN.substring(1);
            }

            Club homeTeam = ClubFunction.getClubByName(homeN).get(0);
            Club awayTeam = ClubFunction.getClubByName(awayN).get(0);

            match.setHomeTeamID(homeTeam);
            match.setAwayTeamID(awayTeam);

            match.setLeagueID(league);
            match.setSeason(currentSeason);

            WebElement timeBlock = driver.findElement(By.xpath("//div[@class='info-block cleared'][3]"));
            List<WebElement> times = timeBlock.findElements(By.cssSelector("dd"));

            String time = times.get(1).getText() + " " + times.get(0).getText();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yy HH:mm");

            match.setTime(sdf.parse(time));

            MatchFunction.addMatch(match);

            // end Match Info Crawling
            // start TimeLine
            List<WebElement> timeLine = driver.findElements(
                    By.xpath("//*[@id=\"live-incidents\"]/div/table/tbody/tr"));

            for (WebElement ele : timeLine) {

                List<WebElement> tds = ele.findElements(By.cssSelector("td"));
                tds.remove(1);
                for (WebElement td : tds) {

                    Goal goal = new Goal();

                    if (td.findElements(By.cssSelector("div")).size() > 1) {
                        List<WebElement> events = td.findElements(By.xpath("./div"));

                        for (WebElement event : events) {

                            if (!event.getAttribute("class").equals("clear")) {

                                String tit = event.getAttribute("title");

                                String playerName = event.findElement(By.cssSelector("a")).getText();

                                WebElement data = event.findElement(By.cssSelector("div"));

                                int minute = Integer.parseInt(data.getAttribute("whoscored.data-minute"));
                                int second = Integer.parseInt(data.getAttribute("whoscored.data-second"));
                                String type = data.getAttribute("whoscored.data-type");

                                Player player = new Player();
                                List<Player> ps = PlayerFunction.findPlayersByName(playerName);
                                for (Player p : ps) {
                                    if (p.getCurrentTeamID().equals(homeTeam) || p.getCurrentTeamID().equals(awayTeam)) {
                                        player = p;
                                        break;
                                    }
                                }

                                switch (type) {
                                    case "16":
                                        goal.setPlayerID(player);
                                        if (tit.contains("OWN")) {
                                            goal.setIsOwnGoad(Boolean.TRUE);
                                        } else {
                                            goal.setIsOwnGoad(Boolean.FALSE);
                                        }
                                        goal.setMatchID(match);
                                        goal.setMinute(minute);
                                        goal.setSecond(second);
                                        break;
                                    case "1":
                                        goal.setAssistPlayerID(player);
                                        break;
                                }
                            }
                        }

                    }
                    // add Goal, YellowCard, RedCard
                    if (goal.getPlayerID() != null) {
                        GoalFunction.addGoal(goal);
                    }
                }

            }

            // end TimeLine
            String homeFormation = driver.findElement(
                    By.xpath("//*[@id=\"match-centre-header\"]/div[1]/div[2]/div[3]")).getText().trim();
            String awayFormation = driver.findElement(
                    By.xpath("//*[@id=\"match-centre-header\"]/div[3]/div[2]/div[3]")).getText().trim();

            // start TeamStat Crawling
            WebElement nextLink = driver.findElement(By.xpath("//*[text() = 'Player Statistics']"));
            String linkStatistic = nextLink.getAttribute("href");

            this.setMainURL(linkStatistic);

            TeamMatchStatistic teamStat1 = new TeamMatchStatistic();
            TeamMatchStatistic teamStat2 = new TeamMatchStatistic();
            teamStat1.setClubID(homeTeam);
            teamStat2.setClubID(awayTeam);
            teamStat1.setMatchID(match);
            teamStat2.setMatchID(match);
            teamStat1.setFormation(homeFormation);
            teamStat2.setFormation(awayFormation);

            List<WebElement> teamStatDivs = driver.findElements(By.xpath("//*[@id=\"match-report-team-statistics\"]/div"));
            List<WebElement> stats = teamStatDivs.get(0).findElements(By.xpath("./div"));

            for (WebElement stat : stats) {
                List<WebElement> spans = stat.findElements(By.xpath("./span"));
                String firstValue = spans.get(0).getText().trim();
                String middleValue = spans.get(1).getText().trim();
                String lastValue = spans.get(2).getText().trim();
                switch (middleValue) {
                    case "Shots":
                        teamStat1.setShots(Integer.parseInt(firstValue));
                        teamStat2.setShots(Integer.parseInt(lastValue));
                        break;
                    case "Shots on target":
                        teamStat1.setShotsOnTarget(Integer.parseInt(firstValue));
                        teamStat2.setShotsOnTarget(Integer.parseInt(lastValue));
                        break;
                    case "Pass Success %":
                        teamStat1.setPassSuccess(Float.parseFloat(firstValue.substring(0, firstValue.length() - 1)));
                        teamStat2.setPassSuccess(Float.parseFloat(lastValue.substring(0, firstValue.length() - 1)));
                        break;
                    case "Aerial Duel Success":
                        teamStat1.setAerialDuelSuccess(Float.parseFloat(firstValue.substring(0, firstValue.length() - 1)));
                        teamStat2.setAerialDuelSuccess(Float.parseFloat(lastValue.substring(0, firstValue.length() - 1)));
                        break;
                    case "Dribbles won":
                        teamStat1.setDribblesWon(Integer.parseInt(firstValue));
                        teamStat2.setDribblesWon(Integer.parseInt(lastValue));
                        break;
                    case "Tackles":
                        teamStat1.setTackles(Integer.parseInt(firstValue));
                        teamStat2.setTackles(Integer.parseInt(lastValue));
                        break;
                }
            }

            List<WebElement> possList = teamStatDivs.get(1).findElements(By.xpath("./div[2]/span/span[@class='stat-value']"));

            String poss1 = possList.get(0).getText();
            String poss2 = possList.get(1).getText();

            teamStat1.setPosession(Float.parseFloat(poss1.substring(0, poss1.length() - 1)));
            teamStat2.setPosession(Float.parseFloat(poss2.substring(0, poss2.length() - 1)));

            TeamMatchStatisticFunction.addTeamMatchStatistic(teamStat1);
            TeamMatchStatisticFunction.addTeamMatchStatistic(teamStat2);

            // end TeamStat Of a Match
            // start PlayerStat Crawling
            List<WebElement> playersStat1 = driver.findElements(
                    By.xpath("//div[@id='statistics-table-home-summary']/table/tbody/tr"));

            for (WebElement playerStat : playersStat1) {
                PlayerMatchStatistic pms = new PlayerMatchStatistic();
                pms.setMatchID(match);

                WebElement pn = playerStat.findElement(By.xpath("./td[@class='pn']"));
                String playerName = pn.findElement(By.xpath("./a")).getText().trim();
                String min = "";
                if (!playerName.matches("([^\\u0000-\\u007F]|\\w|\\ |\\-)+")) {
                    int pos = playerName.indexOf('(');
                    if (pos >= 0) {
                        min = playerName.substring(pos);
                        playerName = playerName.substring(0, pos);
                    }
                }

                Player currentP = PlayerFunction.findByNameAndClub(playerName, homeTeam);
                if (currentP != null) {
                    pms.setPlayerID(currentP);

                    String position = pn.findElement(By.xpath("./span[2]")).getText();
                    position = position.substring(1, position.length()).trim();
                    pms.setPosition(position);

                    if (position.equals("Sub")) {
                        pms.setIsSub(Boolean.TRUE);
                    } else {
                        pms.setIsSub(Boolean.FALSE);
                    }

                    if (!min.isEmpty()) {
                        min = min.replaceAll("\\(", "");
                        min = min.replaceAll("\\)", "");
                        min = min.replaceAll("′", "");
                        if (position.equals("Sub")) {
                            pms.setMinIn(Integer.parseInt(min));
                        } else {
                            pms.setMinOut(Integer.parseInt(min));
                        }
                    }

                    try {
                        String value;
                        value = playerStat.findElement(By.xpath("./td[@class='ShotsTotal ']")).getText().trim();
                        pms.setShots(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='ShotOnTarget ']")).getText().trim();
                        pms.setShotsOnTarget(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='KeyPassTotal ']")).getText().trim();
                        pms.setKeyPasses(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='PassSuccessInMatch ']")).getText().trim();
                        pms.setPa(Float.parseFloat(value));

                        value = playerStat.findElement(By.xpath("./td[@class='DuelAerialWon ']")).getText().trim();
                        pms.setAerialsWon(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='Touches ']")).getText().trim();
                        pms.setTouches(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='rating ']")).getText().trim();
                        pms.setRating(Float.parseFloat(value));
                    } catch (Exception e) {
                    }

                    PlayerMatchStatisticFunction.addPlayerMatchStatistic(pms);
                }

            }

            // end Home Team
            List<WebElement> playersStat2 = driver.findElements(
                    By.xpath("//div[@id='statistics-table-away-summary']/table/tbody/tr"));

            for (WebElement playerStat : playersStat2) {
                PlayerMatchStatistic pms = new PlayerMatchStatistic();
                pms.setMatchID(match);

                WebElement pn = playerStat.findElement(By.xpath("./td[@class='pn']"));
                String playerName = pn.findElement(By.xpath("./a")).getText().trim();
                String min = "";
                if (!playerName.matches("([^\\u0000-\\u007F]|\\w|\\ |\\-)+")) {
                    int pos = playerName.indexOf('(');
                    if (pos >= 0) {
                        min = playerName.substring(pos);
                        playerName = playerName.substring(0, pos);
                    }
                }

                Player currentP = PlayerFunction.findByNameAndClub(playerName, awayTeam);
                if (currentP != null) {
                    pms.setPlayerID(currentP);

                    String position = pn.findElement(By.xpath("./span[2]")).getText();
                    position = position.substring(1, position.length()).trim();
                    pms.setPosition(position);

                    if (!min.isEmpty()) {
                        min = min.replaceAll("\\(", "");
                        min = min.replaceAll("\\)", "");
                        min = min.replaceAll("′", "");
                        if (position.equals("Sub")) {
                            pms.setMinIn(Integer.parseInt(min));
                        } else {
                            pms.setMinOut(Integer.parseInt(min));
                        }
                    }

                    try {
                        String value;
                        value = playerStat.findElement(By.xpath("./td[@class='ShotsTotal ']")).getText().trim();
                        pms.setShots(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='ShotOnTarget ']")).getText().trim();
                        pms.setShotsOnTarget(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='KeyPassTotal ']")).getText().trim();
                        pms.setKeyPasses(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='PassSuccessInMatch ']")).getText().trim();
                        pms.setPa(Float.parseFloat(value));

                        value = playerStat.findElement(By.xpath("./td[@class='DuelAerialWon ']")).getText().trim();
                        pms.setAerialsWon(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='Touches ']")).getText().trim();
                        pms.setTouches(Integer.parseInt(value));

                        value = playerStat.findElement(By.xpath("./td[@class='rating ']")).getText().trim();
                        pms.setRating(Float.parseFloat(value));
                    } catch (Exception e) {
                    }

                    PlayerMatchStatisticFunction.addPlayerMatchStatistic(pms);
                }

            }

            // end Away Team
            System.out.println("Finish! " + i);

        }
    }

    public void crawlCards(League league) {
        final String currentSeason = "2017/2018";
        List<String> matchLinks = new ArrayList<>();
        try {
            matchLinks = this.crawlMatchLink(league);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < matchLinks.size(); i++) {

            this.setMainURL(matchLinks.get(i));
            
            String title = driver.getTitle().trim();
            int posIndex = title.lastIndexOf("-");
            title = title.substring(0, posIndex).trim();
            String[] names = title.split("\\d+-\\d+");
            String homeN = names[0];
            String awayN = names[1];

            while ((homeN.charAt(homeN.length() - 1)) == ' '
                    || (isDigit(homeN.charAt(homeN.length() - 1)))) {
                homeN = homeN.substring(0, homeN.length() - 1);
            }

            while ((awayN.charAt(0)) == ' '
                    || (isDigit(awayN.charAt(0)))) {
                awayN = awayN.substring(1);
            }

            Club homeTeam = ClubFunction.getClubByName(homeN).get(0);
            Club awayTeam = ClubFunction.getClubByName(awayN).get(0);
            
            Match match = MatchFunction.findMatch(homeTeam, awayTeam, currentSeason).get(0);

            // end Match Info Crawling
            // start TimeLine
            List<WebElement> timeLine = driver.findElements(
                    By.xpath("//*[@id=\"live-incidents\"]/div/table/tbody/tr"));

            for (WebElement ele : timeLine) {

                List<WebElement> tds = ele.findElements(By.cssSelector("td"));
                tds.remove(1);
                for (WebElement td : tds) {

                    YellowCard yellowCard = new YellowCard();
                    RedCard redCard = new RedCard();

                    if (td.findElements(By.cssSelector("div")).size() > 1) {
                        List<WebElement> events = td.findElements(By.xpath("./div"));

                        for (WebElement event : events) {

                            if (!event.getAttribute("class").equals("clear")) {

                                String tit = event.getAttribute("title");

                                String playerName = event.findElement(By.cssSelector("a")).getText();

                                WebElement data = event.findElement(By.cssSelector("div"));

                                int minute = Integer.parseInt(data.getAttribute("whoscored.data-minute"));
                                int second = Integer.parseInt(data.getAttribute("whoscored.data-second"));
                                String type = data.getAttribute("whoscored.data-type");

                                Player player = new Player();
                                List<Player> ps = PlayerFunction.findPlayersByName(playerName);
                                for (Player p : ps) {
                                    if (p.getCurrentTeamID().equals(homeTeam) || p.getCurrentTeamID().equals(awayTeam)) {
                                        player = p;
                                        break;
                                    }
                                }

                                switch (type) {
                                    case "17":
                                        String cardType = data.getAttribute("whoscored.data-card-type");
                                        if (cardType.equals("31")) {
                                            yellowCard.setMatchID(match);
                                            yellowCard.setMinute(minute);
                                            yellowCard.setPlayerID(player);
                                        } else if (cardType.equals("32")) {
                                            yellowCard.setMatchID(match);
                                            yellowCard.setMinute(minute);
                                            yellowCard.setPlayerID(player);
                                            redCard.setMatchID(match);
                                            redCard.setMinute(minute);
                                            redCard.setPlayerID(player);
                                        } else {
                                            redCard.setMatchID(match);
                                            redCard.setMinute(minute);
                                            redCard.setPlayerID(player);
                                        }
                                        break;
                                }
                            }
                        }

                    }
                    // YellowCard, RedCard

                    if (yellowCard.getPlayerID() != null) {
                        YellowCardFunction.addYellowCard(yellowCard);
                    }
                    if (redCard.getPlayerID() != null) {
                        RedCardFunction.addRedCard(redCard);
                    }

                }

            }
            
        }
    }
    
}
