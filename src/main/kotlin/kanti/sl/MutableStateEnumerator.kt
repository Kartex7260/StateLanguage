package kanti.sl

import kanti.sl.objects.MutableStateObjects

interface MutableStateEnumerator : StateEnumerator {

	val objects: MutableStateObjects

}