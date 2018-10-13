package sortinghat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Map;

@RestController
public class SortingHatController {

    @Autowired
    SortingHatService sortingHatService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(path = "/rosters/{id}", method = RequestMethod.GET)
    public Roster getRoster(@PathVariable Long id) {
        return sortingHatService.getRoster(id);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(path = "/roster/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map getRosterAsTxt(@PathVariable("id") Long id) {

        return Collections.singletonMap("response", sortingHatService.getRosterAsTxt(id));
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(path = "/roster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map handleFileUpload(@RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        validateFile(file);
        Roster roster = sortingHatService.addRoster(file);
        return Collections.singletonMap("response", roster);
    }

    private void validateFile(MultipartFile file) {
        if (!file.getContentType().equalsIgnoreCase("text/plain"))
            throw new InvalidFileException("It is not a text file");
    }
}
