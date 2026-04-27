package Tests;

import Enemies.*;
import Players.*;
import Game.*;
import Tiles.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test
    public void testWarriorCreation() {
        Position pos = new Position(0, 0);
        Warrior warrior = new Warrior("Jon Snow", pos, 30, 4, 300, 3);
        assertEquals("Jon Snow", warrior.getName());
        assertEquals(1, warrior.getLevel());
        assertEquals(300, warrior.getHealthAmount());
        assertEquals(30, warrior.getAttack());
        assertEquals(4, warrior.getDefense());
    }

    @Test
    public void testMageManaAndSpell() {
        Position pos = new Position(0, 0);
        Mage mage = new Mage("Melisandre", pos, 5, 1, 100, 200, 30, 15, 2, 3);
        assertTrue(mage.getCurrentMana() <= mage.getManaPool());
        assertEquals(15, mage.getSpellPower());

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Monster("Dummy", 'D', new Position(1, 1), 1, 1, 10, 1, 5));
        mage.abilityCast(enemies);
        assertTrue(mage.getCurrentMana() < 200);
    }

    @Test
    public void testHunterShootsAndArrows() {
        Position pos = new Position(0, 0);
        Hunter hunter = new Hunter("Ygritte", pos, 30, 2, 220, 5, 2);
        Monster target = new Monster("Target", 'T', new Position(0, 1), 0, 0, 10, 5, 5);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(target);
        hunter.abilityCast(enemies);
        assertEquals(1, hunter.getArrowsCount());
    }


    @Test
    public void testGameBoardPlacement() {
        char[][] chars = {
                {'#', '.', '.'},
                {'.', '@', '.'},
                {'.', '.', '#'}
        };
        GameBoard board = new GameBoard(chars);
        Position pos = new Position(1, 1);
        Player player = new Warrior("Test", pos, 5, 5, 100, 3);
        board.setTile(1, 1, player);
        assertEquals(player, board.getTile(1, 1));
        board.setChar(1, 1, 'X');
        assertEquals('X', chars[1][1]);
    }

    @Test
    public void testPlayerExperienceAndLevelUp() {
        Position pos = new Position(0, 0);
        Warrior warrior = new Warrior("Leveler", pos, 10, 5, 100, 2);
        assertEquals(1, warrior.getLevel());
        warrior.gainExperience(100);
        assertTrue(warrior.getLevel() > 1);
    }

    @Test
    public void testTrapInvisibility() {
        Trap trap = new Trap("Death Trap", 'D', new Position(0, 0), 10, 5, 50, 100, 1, 1);
        Player dummy = new Warrior("Dummy", new Position(1, 0), 0, 0, 100, 1);
        for (int i = 0; i < 3; i++) {
            trap.gameTick(dummy);
        }
        assertTrue(trap.getTile() == 'D' || trap.getTile() == '.');
    }

    @Test
    public void testMonsterMovementTowardPlayer() {
        Player player = new Rogue("Arya", new Position(2, 2), 30, 5, 100, 20);
        Monster monster = new Monster("Walker", 'W', new Position(0, 0), 10, 5, 50, 10, 20);
        Position nextPos = monster.gameTick(player);
        assertNotEquals(new Position(0, 0), nextPos);
    }

    @Test
    public void testAbilityKillsEnemy() {
        Mage mage = new Mage("Fire", new Position(0, 0), 999999999, 1, 100, 200, 30, 50, 50, 3);
        Monster enemy = new Monster("Weakling", 'E', new Position(1, 0), 1, 0, 1, 2, 5); // Changed HP to 1
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);
        mage.abilityCast(enemies);
        assertTrue(enemy.getHealthAmount() <= 0);
    }
    @Test
    public void testMageAbilityDealsDamage() {
        Mage mage = new Mage("Fire", new Position(0, 0), 9999999, 5, 100, 100, 10, 15, 1, 3);
        mage.setCurrentMana(10);

        Monster enemy = new Monster("Target", 'T', new Position(0, 1), 1, 0, 30, 3, 10);
        int healthBefore = enemy.getHealthAmount();

        mage.abilityCast(List.of(enemy));

        assertTrue(enemy.getHealthAmount() < healthBefore, "Enemy should have taken damage from ability");
    }

    @Test
    public void testMageAbilityKillsEnemyAndGainsXP() {
        Mage mage = new Mage("Fire", new Position(0, 0), 99999999, 5, 100, 100, 10, 15, 1, 3);
        mage.setCurrentMana(10);

        Monster enemy = new Monster("Weakling", 'W', new Position(0, 1), 1, 0, 1, 3, 50);
        int xpBefore = mage.getExperience();
        int levelBefore = mage.getLevel();

        mage.abilityCast(List.of(enemy));

        assertTrue(enemy.getHealthAmount() <= 0, "Enemy should be dead after ability cast");
        assertTrue(mage.getExperience() > xpBefore || mage.getLevel() > levelBefore,
                "Mage should gain experience after killing enemy");
    }


    @Test
    public void testPlayerVisitEnemy() {
        Player player = new Warrior("Tester", new Position(1, 1), 99999999, 0, 100, 3);
        Monster enemy = new Monster("Dummy", 'D', new Position(1, 2), 0, 0, 10, 1, 5);
        boolean result = player.visit(enemy);
        assertTrue(result || enemy.getHealthAmount() < 10);
    }
    @Test
    public void testPlayerCannotCastWithoutMana() {
        Mage mage = new Mage("EmptyMage", new Position(0, 0), 5, 1, 100, 50, 50, 15, 1, 3); // manaPool = 50, manaCost = 50
        mage.abilityCast(new ArrayList<>());
        int manaBefore = mage.getCurrentMana();
        mage.abilityCast(new ArrayList<>());
        assertEquals(manaBefore, mage.getCurrentMana(), "Ability cast with no mana should fail");
    }

    @Test
    public void testTrapBecomesInvisibleAndBack() {
        Trap trap = new Trap("Flasher", 'F', new Position(0, 0), 1, 1, 10, 1, 1, 1);
        Player dummy = new Warrior("Dummy", new Position(1, 0), 0, 0, 100, 1);
        trap.gameTick(dummy); // tick 0: visible
        assertEquals('F', trap.getTile());
        trap.gameTick(dummy); // tick 1: invisible
        assertEquals('.', trap.getTile());
        trap.gameTick(dummy); // tick 2: back to visible
        assertEquals('F', trap.getTile());
    }

    @Test
    public void testPlayerCannotMoveIntoWall() {
        Warrior player = new Warrior("Tester", new Position(1, 1), 10, 5, 100, 3);
        Tile wall = new Wall(new Position(1, 0));
        assertFalse(player.visit(wall), "Player should not be able to move into wall");
    }

    @Test
    public void testWarriorLevelUp() {
        Warrior warrior = new Warrior("TestWarrior", new Position(0, 0), 10, 5, 100, 3);
        int initialHealth = warrior.getHealthPool();
        warrior.setExperience(50);
        warrior.levelUp();
        assertEquals(2, warrior.getLevel());
        assertTrue(warrior.getHealthPool() > initialHealth);
    }

    @Test
    public void testMageManaRegen() {
        Mage mage = new Mage("TestMage", new Position(0, 0), 10, 5, 100, 100, 10, 20, 3, 5);
        int manaBefore = mage.getCurrentMana();
        mage.gameTick();
        assertTrue(mage.getCurrentMana() > manaBefore);
    }

    @Test
    public void testRogueAbilityCast() {
        Rogue rogue = new Rogue("TestRogue", new Position(0, 0), 10, 5, 100, 20);
        rogue.abilityCast(new ArrayList<>());
        assertEquals(80, rogue.getCurrentEnergy());
    }

    @Test
    public void testHunterArrowsIncrement() {
        Hunter hunter = new Hunter("TestHunter", new Position(0, 0), 10, 5, 100, 5, 1);
        for (int i = 0; i <= 10; i++) hunter.gameTick();
        assertEquals(2, hunter.getArrowsCount());
    }

    @Test
    public void testTrapVisibilityToggle() {
        Trap trap = new Trap("Trap", 'T', new Position(0, 0), 10, 5, 100, 10, 1, 1);
        trap.gameTick(new Warrior("dummy", new Position(1, 1), 1, 1, 1, 1));
        char tileAfter1 = trap.getTile();
        trap.gameTick(new Warrior("dummy", new Position(1, 1), 1, 1, 1, 1));
        char tileAfter2 = trap.getTile();
        assertNotEquals(tileAfter1, tileAfter2);
    }

    @Test
    public void testMonsterApproachesPlayer() {
        Monster monster = new Monster("Monster", 'M', new Position(0, 0), 10, 5, 100, 10, 10);
        Player player = new Rogue("Player", new Position(5, 5), 10, 5, 100, 20);
        Position nextMove = monster.gameTick(player);
        assertNotEquals(monster.getPosition(), nextMove);
    }

    @Test
    public void testPlayerAttacksEnemy() {
        Warrior warrior = new Warrior("Warrior", new Position(0, 0), 999999999, 5, 100, 3);
        Monster monster = new Monster("Monster", 'M', new Position(0, 1), 10, 0, 50, 3, 10);
        warrior.visit(monster);
        assertTrue(monster.getHealthAmount() < 50);
    }

    @Test
    public void testGameBoardSetGet() {
        char[][] layout = {{'.', '.'}, {'.', '.'}};
        GameBoard board = new GameBoard(layout);
        Empty tile = new Empty(new Position(0, 0));
        board.setTile(0, 0, tile);
        assertEquals(tile, board.getTile(0, 0));
    }

    @Test
    public void testTrapDamagesPlayer() {
        Trap trap = new Trap("Trap", 'T', new Position(0, 0), 100, 0, 100, 100, 10, 10);
        Warrior warrior = new Warrior("Warrior", new Position(0, 1), 999999999, 0, 100, 3);
        trap.gameTick(warrior);
        assertTrue(warrior.getHealthAmount() < 100);
    }

    @Test
    public void testMageCastWithExactMana() {
        Mage mage = new Mage("TestMage", new Position(0, 0), 99999999, 5, 100, 40, 10, 15, 1, 3);
        mage.setCurrentMana(10);
        Monster m = new Monster("M", 'm', new Position(1, 1), 5, 2, 30, 3, 10);
        mage.abilityCast(List.of(m));
        assertTrue(m.getHealthAmount() < 30);
    }

    @Test
    public void testHunterShootNearestEnemy() {
        Hunter hunter = new Hunter("Hunter", new Position(0, 0), 99999999, 2, 100, 5, 1);
        Monster m1 = new Monster("m1", 'm', new Position(0, 1), 5, 0, 30, 3, 10);
        Monster m2 = new Monster("m2", 'm', new Position(5, 5), 5, 0, 30, 3, 10);
        hunter.abilityCast(List.of(m1, m2));
        assertTrue(m1.getHealthAmount() < 30);
    }

    @Test
    public void testRogueCantCastWithoutEnergy() {
        Rogue rogue = new Rogue("Rogue", new Position(0, 0), 10, 5, 100, 50);
        rogue.setCurrentEnergy(10);
        rogue.abilityCast(new ArrayList<>());
        assertEquals(10, rogue.getCurrentEnergy());
    }

    @Test
    public void testWarriorHealOnAbilityCast() {
        Warrior warrior = new Warrior("Warrior", new Position(0, 0), 10, 5, 100, 3);
        warrior.setHealthAmount(20);
        Monster m = new Monster("m", 'm', new Position(1, 1), 5, 0, 30, 3, 10);
        warrior.abilityCast(List.of(m));
        assertTrue(warrior.getHealthAmount() > 20);
    }

    @Test
    public void testEnemyCannotMoveIntoWall() {
        Monster monster = new Monster("Walker", 'W', new Position(1, 1), 10, 5, 50, 3, 10);
        Wall wall = new Wall(new Position(1, 2));
        Position before = monster.getPosition();

        boolean result = monster.visit(wall);

        assertFalse(result, "Monster should not be able to move into a wall");
        assertEquals(before, monster.getPosition(), "Monster position should not change after hitting wall");
    }

    @Test
    public void testTrapDoesNotAttackFromFar() {
        Trap trap = new Trap("Trap", 'T', new Position(0, 0), 100, 10, 100, 1, 2, 2);
        Warrior warrior = new Warrior("Warrior", new Position(5, 5), 10, 5, 100, 1);
        int healthBefore = warrior.getHealthAmount();
        trap.gameTick(warrior);
        assertEquals(healthBefore, warrior.getHealthAmount());
    }
    @Test
    public void testMageNoEnemiesInRange() {
        Mage mage = new Mage("Fire", new Position(0, 0), 10, 5, 100, 100, 10, 15, 1, 3);
        Monster far = new Monster("M", 'm', new Position(10, 10), 5, 5, 100, 3, 10);
        int manaBefore = mage.getCurrentMana();
        mage.abilityCast(List.of(far));
        assertEquals(manaBefore - 10, mage.getCurrentMana());
    }
    @Test
    public void testMonsterRandomMovementWhenPlayerOutOfSight() {
        Player player = new Rogue("R", new Position(10, 10), 10, 1, 100, 10);
        Monster monster = new Monster("M", 'M', new Position(1, 1), 10, 5, 100, 2, 10); // vision range קטן מדי
        Position next = monster.gameTick(player);
        assertNotEquals(new Position(10, 10), next);
    }
    @Test
    public void testWarriorAbilityBlockedDuringCooldown() {
        Warrior warrior = new Warrior("Test", new Position(0, 0), 10, 5, 100, 2);
        warrior.abilityCast(List.of(new Monster("Dummy", 'D', new Position(1, 1), 1, 0, 5, 1, 5)));
        int cooldown = warrior.getRemainingCooldown();
        warrior.abilityCast(new ArrayList<>());
        assertEquals(cooldown, warrior.getRemainingCooldown(), "Ability should be blocked during cooldown");
    }
}
