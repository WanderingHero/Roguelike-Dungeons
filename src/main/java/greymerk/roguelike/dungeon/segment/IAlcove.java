package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IAlcove {

    public void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord pos, Cardinal dir);

    public boolean isValidLocation(IWorldEditor editor, Coord pos, Cardinal dir);

}
