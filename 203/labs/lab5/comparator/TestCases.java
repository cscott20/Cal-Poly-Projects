import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import org.junit.Test;
import org.junit.Before;

public class TestCases
{
    private static final Song[] songs = new Song[] {
        new Song("Decemberists", "The Mariner's Revenge Song", 2005),
        new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
        new Song("Avett Brothers", "Talk on Indolence", 2006),
        new Song("Gerry Rafferty", "Baker Street", 1998),
        new Song("City and Colour", "Sleeping Sickness", 2007),
        new Song("Foo Fighters", "Baker Street", 1997),
        new Song("Queen", "Bohemian Rhapsody", 1975),
        new Song("Gerry Rafferty", "Baker Street", 1978)
       };

    @Test
    public void testArtistComparator()
    {
     ArtistComparator artComp = new ArtistComparator();
     assertTrue(artComp.compare(songs[0], songs[1]) < 0);
    }

    @Test
    public void testLambdaTitleComparator()
    {
     Comparator<Song> titleComp = ( (song1, song2)->song1.getTitle().compareTo(song2.getTitle()));

    }

    @Test
    public void testYearExtractorComparator()
    {
     Comparator<Song> yearComp = Comparator.comparing(Song::getYear).reversed();
     assertTrue(yearComp.compare(songs[1],songs[2]) > 0);

    }

    @Test
    public void testComposedComparator()
    {
        ArtistComparator artComp = new ArtistComparator();
        Comparator<Song> yearComp = Comparator.comparing(Song::getYear);
        ComposedComparator composed = new ComposedComparator(artComp, yearComp);
        assertTrue(composed.compare(songs[3], songs[7]) > 0);
    }

    @Test
    public void testThenComparing()
    {
        ArtistComparator artComp = new ArtistComparator();
        Comparator<Song> yearComp = Comparator.comparing(Song::getYear);
        Comparator<Song> thencomp = artComp.thenComparing(yearComp);
        assertTrue(thencomp.compare(songs[3], songs[7]) > 0);
    }

    @Test
    public void runSort()
    {
       List<Song> songList = new ArrayList<>(Arrays.asList(songs));
       List<Song> expectedList = Arrays.asList(
        new Song("Avett Brothers", "Talk on Indolence", 2006),
        new Song("City and Colour", "Sleeping Sickness", 2007),
        new Song("Decemberists", "The Mariner's Revenge Song", 2005),
        new Song("Foo Fighters", "Baker Street", 1997),
        new Song("Gerry Rafferty", "Baker Street", 1978),
        new Song("Gerry Rafferty", "Baker Street", 1998),
        new Song("Queen", "Bohemian Rhapsody", 1975),
        new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
          );
        ArtistComparator artComp = new ArtistComparator();
    Comparator<Song> titleComp = ( (song1, song2)->song1.getTitle().compareTo(song2.getTitle()));
        Comparator<Song> yearComp = Comparator.comparing(Song::getYear);
        songList.sort(artComp.thenComparing(titleComp).thenComparing(yearComp));
    assertEquals(songList, expectedList);
    }
}
