class InstanceCatalog {
    constructor(id = "instances") {
        this.targetElement = document.getElementById(id);
    }
    add(instance) {
        const instanceLi = document.createElement("li");
        instanceLi.appendChild(document.createTextNode(instance.gun.name));
        this.targetElement.appendChild(instanceLi);
    }
    addAll(instances = []) {
        instances.forEach(this.add.bind(this));
    }
}
//# sourceMappingURL=InstanceCatalog.js.map