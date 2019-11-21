package se.complexjava.videostreamingapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.primitives.Longs.min;


@RestController
public class VideoController {

    private static Logger logger = LoggerFactory.getLogger(VideoController.class);

    @GetMapping("/videos/{name}")
    public ResponseEntity<ResourceRegion> getVideo(@PathVariable(name = "name") String name, @RequestHeader HttpHeaders headers) throws Exception{

        logger.info("GetVideo called");

        Path file = Paths.get("./videos/" + name);

        logger.info("Video: {}", file.toUri());

        UrlResource video = new UrlResource(file.toUri());

        ResourceRegion newResource = resourceRegion(video, headers);

        logger.info("Creating response");


        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory
                        .getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(newResource);
    }


    private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws Exception{

        long contentLength = video.contentLength();
        List<HttpRange> range = headers.getRange();

        if(!range.isEmpty()){

            long start = range.get(0).getRangeStart(contentLength);//make this less hardcoded
            long end = range.get(0).getRangeEnd(contentLength);//    -//-
            long rangeLength = min(1 * 1024 * 1024, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);

        }else{

            long rangeLength = min(1 * 1024 * 1024, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }
}
