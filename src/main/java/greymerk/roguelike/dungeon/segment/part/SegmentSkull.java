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
import greymerk.roguelike.worldgen.blocks.Skull;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSkull extends SegmentBase {

    @Override
    protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
            Coord origin) {

        MetaBlock air = BlockType.get(BlockType.AIR);
        IStair stair = theme.getSecondaryStair();

        Coord cursor;
        Coord start;
        Coord end;

        Cardinal[] orth = Cardinal.orthogonal(dir);

        start = new Coord(origin);
        start.add(dir, 2);
        end = new Coord(start);
        start.add(orth[0], 1);
        end.add(orth[1], 1);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, air);
        start.add(dir, 1);
        end.add(dir, 1);
        RectSolid.fill(editor, rand, start, end, theme.getSecondaryWall());

        for (Cardinal d : orth) {
            cursor = new Coord(origin);
            cursor.add(Cardinal.UP, 2);
            cursor.add(dir, 2);
            cursor.add(d, 1);
            stair.set(editor, cursor);

            cursor = new Coord(origin);
            cursor.add(dir, 2);
            cursor.add(d, 1);
            stair.setOrientation(Cardinal.reverse(d), false);
            stair.set(editor, cursor);
        }

        cursor = new Coord(origin);
        cursor.add(Cardinal.UP, 1);
        cursor.add(dir, 3);
        air.set(editor, cursor);
        cursor.add(Cardinal.UP, 1);
        stair.setOrientation(Cardinal.reverse(dir), true);
        stair.set(editor, cursor);

        Coord shelf = new Coord(origin);
        shelf.add(dir, 3);
        Coord below = new Coord(shelf);
        shelf.add(Cardinal.UP, 1);

        if (editor.isAirBlock(below)) return;

        Skull type;
        if (rand.nextInt(5) == 0) {
            type = Skull.ZOMBIE;
        } else {
            type = rand.nextInt(10) == 0 ? Skull.WITHER : Skull.SKELETON;
            Skull.set(editor, rand, shelf, Cardinal.reverse(dir), type);
        }
    }
}
