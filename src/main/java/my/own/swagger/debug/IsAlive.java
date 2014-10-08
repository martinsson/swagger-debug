package my.own.swagger.debug;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Component
@Path("/alive")
@Api(value="/alive")
public class IsAlive {
	
	@ApiOperation(value="Server is alive")
	@GET
	@Produces({ "text/plain" })
	@Path("/isAlive")
	public String alive() {
		return "I'm here";
	}

}
