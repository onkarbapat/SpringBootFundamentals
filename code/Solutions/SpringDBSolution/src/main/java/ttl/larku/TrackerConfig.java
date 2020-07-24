package ttl.larku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.inmemory.InMemoryTrackDAO;
import ttl.larku.dao.jpahibernate.JPATrackDAO;
import ttl.larku.dao.jpahibernate.TrackRepository;
import ttl.larku.domain.Track;
import ttl.larku.service.TrackRepoService;
import ttl.larku.service.TrackService;

@Configuration
public class TrackerConfig {

    @Autowired
    private Environment env;

    private TrackerTestDataConfig testDataProducer = new TrackerTestDataConfig();

    @Bean
    @Profile("development")
    public BaseDAO<Track> trackDAO() {
        // return inMemoryStudentDAO();
        return testDataProducer.trackDAOWithInitData();
    }

    @Bean(name = "trackDAO")
    @Profile("production")
    public BaseDAO<Track> trackDAOJpa() {
        return jpaTrackDAO();
    }


    @Bean
    public BaseDAO<Track> inMemoryTrackDAO() {
        return new InMemoryTrackDAO();
    }

    @Bean
    @Profile("production")
    public BaseDAO<Track> jpaTrackDAO() {
        JPATrackDAO dao = new JPATrackDAO();
        return dao;
    }

    @Bean
    public TrackService trackService() {
        TrackService ss = new TrackService();
        ss.setTrackDAO(trackDAO());

        return ss;
    }

    @Bean
    public TrackRepoService trackRepoService(TrackRepository trackRepo) {
        TrackRepoService ss = new TrackRepoService(trackRepo);

        return ss;
    }
}