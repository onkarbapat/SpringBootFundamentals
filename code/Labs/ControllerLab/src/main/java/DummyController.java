//TODO - put this class into an appropriate package

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.service.TrackService;

//TODO - Add an annotation to make this class a Controller.  
//TODO - Add an annotation to set the base URL for Track resources to "/track"
@RestController
@RequestMapping("/track")
public class DummyController {

    public String boo() {
        return "boo";
    }

    //TODO - Make sure this reference gets set somehow
    private TrackService trackService;

    //TODO - Set this method up to
    //     - service Get requests to the base URL to return
    //     - all tracks.
    //     - And, obviously, implement the method
    public ResponseEntity<?> getAllTracks() {
        return null;
    }

    //TODO - Set this method up to service Get requests for a
    //     - for a single Track resource by id.  The id should
    //     - be passed in as part of the URL
    public ResponseEntity<?> getTrack() {
        return null;
    }

    //TODO - Set up the remaining methods asked for in the Lab
}
