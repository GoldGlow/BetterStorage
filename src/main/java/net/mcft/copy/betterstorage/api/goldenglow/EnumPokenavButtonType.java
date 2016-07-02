package net.mcft.copy.betterstorage.api.goldenglow;

public enum EnumPokenavButtonType {
    Blank ("", new int[] {0, 0}),
    Back ("Back", new int[] {32, 45}),
    Follower ("Followers", new int[] {0, 45}),
    Fly ("Fly", new int[] {16, 45}),
    FlyTown ("Fly here", new int[] {48, 45}),
    Matchup ("Matchup Checker", new int[] {0, 61}),
    Costumes ("Costumes", new int[] {16, 61}),
    Colour ("", new int[] {0, 0}),
    Hat ("Head", new int[] {0, 77}),
    Torso ("Torso", new int[] {16, 77}),
    Legs ("Legs", new int[] {32, 77}),
    Feet ("Feet", new int[] {48, 77}),
    Extra ("Extras", new int[] {64, 77}),
    Clear ("Clear Layer", new int[] {80, 77}),
    Save ("Save", new int[] {0, 93});

    public String tip;
    public int[] iconCoords;

    EnumPokenavButtonType(String tip, int[] iconCoords) {
        this.tip=tip;
        this.iconCoords = iconCoords;
    }
}
