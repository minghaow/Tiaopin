package nanshen.data.Dtree;

import java.util.ArrayList;
import java.util.List;

/**
 * DtreeTraDtreeTrackListck
 *
 * @Author WANG Minghao
 */
public class DtreeTrackList {

    private List<DtreeTrack> dtreeTrackList = new ArrayList<DtreeTrack>();

    public DtreeTrackList() {
    }

    public DtreeTrackList(List<DtreeTrack> dtreeTrackList) {
        this.dtreeTrackList = dtreeTrackList;
    }

    public List<DtreeTrack> getDtreeTrackList() {
        return dtreeTrackList;
    }

    public void setDtreeTrackList(List<DtreeTrack> dtreeTrackList) {
        this.dtreeTrackList = dtreeTrackList;
    }
}
