## 0. Notre exemple

Je vais utiliser un exemple concret pour clarifier le processus, mais bien sûr, le processus expliqué ici est le même pour tout ensemble de données.

Prenons l'exemple suivant. Vous disposez d'un ensemble de données sur les objets du trafic routier. Laissez cet ensemble de données contenir des annotations pour les classes d'objets d'intérêt suivantes :

['voiture', 'camion', 'piéton', 'cycliste', 'feu_de_circulation', 'moto', 'bus', 'panneau_d'arrêt']``.

Autrement dit, votre jeu de données contient des annotations pour 8 classes d'objets.

Vous souhaitez maintenant former un SSD300 sur cet ensemble de données. Cependant, au lieu de vous donner la peine de former un nouveau modèle à partir de zéro, vous voudriez plutôt utiliser le modèle original SSD300 entièrement formé sur MS COCO et l'ajuster sur votre jeu de données.

Le problème est le suivant : Le SSD300 qui a été formé sur MS COCO prévoit 80 classes différentes, mais votre jeu de données ne comporte que 8 classes. Les tenseurs de poids des couches de classification du modèle MS COCO n'ont pas la bonne forme pour votre modèle qui est censé n'apprendre que 8 classes. C'est dommage.

Alors quelles sont les options qui s'offrent à nous ?

### Option 1 : Ne pas tenir compte du fait que nous n'avons besoin que de 8 classes

L'option peut-être pas si évidente mais tout à fait évidente est la suivante : nous pourrions simplement ignorer le fait que le modèle MS COCO formé prévoit 80 classes différentes, mais nous ne voulons l'affiner que sur 8 classes. Nous pourrions simplement faire correspondre les 8 classes de notre ensemble de données annotées à 8 indices sur les 80 que prévoit le modèle MS COCO. Les ID de classe dans notre ensemble de données pourraient être les indices 1-8, ils pourraient être les indices `[0, 3, 8, 1, 2, 10, 4, 6, 12]`, ou tout autre 8 sur les 80. Peu importe ce que nous les choisirions. Le fait est que nous ne formerions que 8 neurones sur 80 qui prédisent la classe pour une boîte donnée et que les 72 autres ne seraient tout simplement pas formés. Il ne leur arriverait rien, car le gradient pour eux serait toujours nul, car ces indices n'apparaissent pas dans notre ensemble de données.

Cela fonctionnerait, et ce ne serait même pas une option terrible. Comme seules 8 classes sur 80 seraient formées, le modèle pourrait s'aggraver progressivement pour prédire les 72 autres classes, mais nous ne nous en soucions pas de toute façon, du moins pas pour l'instant. Et si nous réalisons un jour que nous voulons maintenant prédire plus de 8 classes différentes, notre modèle serait extensible en ce sens. Toute nouvelle classe que nous voudrions ajouter pourrait simplement obtenir n'importe lequel des indices libres restants comme identifiant. Nous n'aurions pas besoin de changer quoi que ce soit au modèle, il suffirait de faire annoter l'ensemble des données en conséquence.

Néanmoins, dans cet exemple, nous ne voulons pas suivre cette voie. Nous ne voulons pas nous encombrer de la surcharge de calcul que représentent des couches de classification trop complexes, dont 90 % ne sont pas utilisées de toute façon, mais dont la totalité des résultats doit être calculée à chaque passage.

Alors que pourrions-nous faire d'autre à la place ?

### Option 2 : ignorer les poids qui posent problème

Nous pourrions construire un nouveau SSD300 avec 8 classes et y charger les poids du MS COCO SSD300 pour toutes les couches sauf les couches de classification. Est-ce que cela fonctionnerait ? Oui, cela fonctionnerait. Le seul conflit concerne les poids des couches de classification, et nous pouvons éviter ce conflit en les ignorant tout simplement. Bien que cette solution soit facile, elle présente un inconvénient important : si nous ne chargeons pas des poids formés pour les couches de classification de notre nouveau modèle SSD300, ils seront initialisés de manière aléatoire. Nous bénéficierions toujours des poids formés pour toutes les autres couches, mais les couches de classification devraient être formées à partir de zéro.

Ce n'est pas la fin du monde, mais nous aimons les trucs préformés, car cela nous fait gagner beaucoup de temps de formation. Alors que pourrions-nous faire d'autre ?

### Option 3 : Sous-échantillonner les poids qui posent problème

Au lieu de jeter les poids problématiques comme dans l'option 2, nous pourrions également les sous-échantillonner. Si les tenseurs de poids des couches de classification du modèle MS COCO n'ont pas la bonne forme pour notre nouveau modèle, nous les **faireons** simplement avoir la bonne forme. De cette façon, nous pourrons toujours bénéficier des poids préformés dans ces couches de classification. Cela semble bien mieux que l'option 2.

Ce qui est formidable dans cet exemple, c'est que Le MS COCO contient les huit classes qui nous intéressent. Ainsi, lorsque nous sous-échantillonnons les tenseurs de poids des couches de classification, nous ne le faisons pas au hasard. Au lieu de cela, nous allons choisir exactement les éléments du tenseur qui sont responsables de la classification des 8 classes qui nous intéressent.

Cependant, même si les classes de votre ensemble de données étaient entièrement différentes des classes de n'importe quel modèle entièrement formé, il serait tout de même très judicieux d'utiliser les poids du modèle entièrement formé. Tout poids formé est toujours un meilleur point de départ pour la formation que l'initialisation aléatoire, même si votre modèle sera formé sur des classes d'objets entièrement différentes.

Et bien sûr, au cas où vous auriez le problème inverse, c'est-à-dire si votre jeu de données comporte **plus** de classes que le modèle formé que vous souhaitez affiner, vous pouvez simplement faire 
