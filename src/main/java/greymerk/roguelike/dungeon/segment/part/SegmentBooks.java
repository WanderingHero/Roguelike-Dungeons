package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentBooks extends SegmentBase {

    @Override
    protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
            Coord origin) {

        MetaBlock air = BlockType.get(BlockType.AIR);
        IStair stair = theme.getSecondaryStair();

        Coord cursor = new Coord(origin);
        Coord start;
        Coord end;

        Cardinal[] orth = Cardinal.orthogonal(dir);

        cursor.add(dir, 2);
        start = new Coord(cursor);
        start.add(orth[0], 1);
        end = new Coord(cursor);
        end.add(orth[1], 1);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, air);

        level.getSettings().getSecrets().genRoom(editor, rand, level.getSettings(), dir, new Coord(origin));

        start.add(dir, 1);
        end.add(dir, 1);
        RectSolid.fill(editor, rand, start, end, theme.getSecondaryWall(), false, true);

        cursor.add(Cardinal.UP, 2);
        for (Cardinal d : orth) {
            Coord c = new Coord(cursor);
            c.add(d, 1);
            stair.setOrientation(Cardinal.reverse(d), true);
            stair.set(editor, c);
        }

        cursor = new Coord(origin);
        cursor.add(dir, 3);
        BlockType.get(BlockType.SHELF).set(editor, cursor);
        cursor.add(Cardinal.UP);
        BlockType.get(BlockType.SHELF).set(editor, cursor);
    }
}
