package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event create(EventDetailDto event) {
        LOG.trace("create({})", event);
        Event debug = eventMapper.eventDetailDtoToEvent(event);
        return eventRepository.save(debug);
    }

    @Override
    public Page<Event> findAllPagesByDate(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("date").ascending());

        LOG.debug("Find all event entries by pageable: {}", pageable);
        return eventRepository.findAll(pageable);
    }
}
