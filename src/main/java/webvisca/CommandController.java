package webvisca;

import jssc.SerialPortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/visca")
public class CommandController {

    private CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @RequestMapping("/{command}")
    @GetMapping
    public  @ResponseBody String getCommand(@PathVariable(value = "command") String command,
                                            @RequestParam(value = "speed", required = false) Byte speed,
                                            @RequestParam(value = "camno", required = false) Byte camNo, ModelMap model) {
        try {
            return command + " " + speed + " " + camNo + commandService.handleCommand(command, speed, camNo);
        } catch (ViscaResponseReader.TimeoutException | SerialPortException e) {
            return "error";
        }
    }

}
