//noinspection JSUnusedGlobalSymbols
class InstanceCatalog {

    private readonly targetElement: HTMLOListElement;

    //noinspection JSUnusedGlobalSymbols
    constructor(id = "instances") {
        this.targetElement = document.getElementById(id) as HTMLOListElement;
    }

    public add(instance: Instance) {
        //noinspection JSValidateTypes
        const instanceLi: HTMLLIElement = document.createElement("li");

        instanceLi.appendChild(document.createTextNode(instance.gun.name));

        this.targetElement.appendChild(instanceLi);
    }

    //noinspection JSUnusedGlobalSymbols
    public addAll(instances: Array<Instance> = []) {
        instances.forEach(this.add.bind(this));
    }
}
