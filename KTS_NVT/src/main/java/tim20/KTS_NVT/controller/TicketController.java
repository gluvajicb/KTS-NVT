package tim20.KTS_NVT.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.model.*;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.TicketService;

import java.util.*;


@Controller
@RequestMapping(value = "/tickets")
public class TicketController
{



}
