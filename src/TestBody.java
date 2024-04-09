public class TestBody extends Plane{
    public TestBody(ColorfulWorld world) {
        super(world);
        this.setWeapon(new MachineGun(this));
    }
}
