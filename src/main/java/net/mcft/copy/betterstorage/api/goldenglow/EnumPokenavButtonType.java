package net.mcft.copy.betterstorage.api.goldenglow;

public enum EnumPokenavButtonType {
    Blank ("", new int[] {0, 0}),
    Back ("Back", new int[] {32, 46}),
    Follower ("Followers", new int[] {0, 46}),
    Fly ("Fly", new int[] {16, 46}),
    FlyTown ("Fly here", new int[] {48, 46}),
    Matchup ("Matchup Checker", new int[] {0, 62}),
    Colour ("", new int[] {0, 0});

    public String tip;
    public int[] iconCoords;

    EnumPokenavButtonType(String tip, int[] iconCoords) {
        this.tip=tip;
        this.iconCoords = iconCoords;
    }
}
