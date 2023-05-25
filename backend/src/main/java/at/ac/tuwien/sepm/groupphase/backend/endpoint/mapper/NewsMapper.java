package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.AbbreviatedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.mapstruct.*;

import java.util.*;

@Mapper
public interface NewsMapper {

    @Named("stringListToNewsImageList")
    static List<NewsImage> toNewsImageList(List<String> images) {
        if (images == null) {
            return null;
        }
        List<NewsImage> entityImages = new LinkedList<>();
        for (String str : images) {
            entityImages.add(NewsImage.NewsImageBuilder.aNewsImage().withImageData(str).build());
        }
        return entityImages;
    }

    @Named("newsImageListToStringList")
    static List<String> toStringList(List<NewsImage> images) {
        if (images == null) {
            return null;
        }
        List<String> stringImages = new LinkedList<>();
        for (NewsImage img : images) {
            stringImages.add(img.getImageData());
        }
        return stringImages;
    }

    @Named("eventToEventId")
    static Long eventToEventId(Event event) {
        if (event == null) {
            return null;
        }
        return event.getId();
    }

    AbbreviatedNewsDto newsToAbbreviatedNewsDto(News news);

    @Mapping(source = "images", target = "images", qualifiedByName = "newsImageListToStringList")
    @Mapping(source = "event", target = "eventId", qualifiedByName = "eventToEventId")
    DetailedNewsDto newsToDetailedNewsDto(News news);

    @Mapping(source = "images", target = "images", qualifiedByName = "stringListToNewsImageList")
    News newsInquiryDtoWithImagesToNewsWithoutEvent(NewsInquiryDto newsInquiryDto);

    @Mapping(source = "images", target = "images", qualifiedByName = "newsImageListToStringList")
    @Mapping(source = "event", target = "eventId", qualifiedByName = "eventToEventId")
    NewsInquiryDto newsToNewsInquiryDto(News news);
}
