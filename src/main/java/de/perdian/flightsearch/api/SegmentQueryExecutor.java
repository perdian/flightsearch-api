package de.perdian.flightsearch.api;

import java.io.IOException;
import java.util.List;

import de.perdian.flightsearch.api.model.Segment;
import de.perdian.flightsearch.api.model.SegmentQuery;

public interface SegmentQueryExecutor {

    List<Segment> loadSegment(SegmentQuery segmentQuery) throws IOException;

}
