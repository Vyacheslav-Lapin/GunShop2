'use strict';

class InstanceCatalog {

    /**
     * @param {string} id
     */
    constructor(id="instances") {
        /**
         * @private
         * @type HTMLOListElement
         */
        this.targetElement = document.getElementById(id);
    }

    /**
     * @param {Instance} instance
     */
    add(instance) {
        //noinspection JSValidateTypes
        const /** @type HTMLLIElement */ instanceLi = document.createElement("li");

        instanceLi.appendChild(document.createTextNode(instance.gun.name));

        this.targetElement.appendChild(instanceLi);
    }

    /**
     * @param {Array<Instance>} instances
     */
    addAll(instances=[]) {
        instances.forEach(this.add.bind(this));
    }
}