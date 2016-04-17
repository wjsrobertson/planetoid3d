describe("Planetoid", function() {
    it("calls a fake method", function() {
        var fake = Planetoid.fake();

        expect(fake).toBe(1);
    })
});
